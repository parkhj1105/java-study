package user;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import com.toedter.calendar.JDateChooser;

import reservationsystem.LoginFrame;
import util.DbConn; // DbConn 클래스를 import 합니다.

public class UserReservationFrame extends JFrame {

    JComboBox<String> t1 = new JComboBox<>();
    JDateChooser t2 = new JDateChooser();
    JSpinner timeSpinner;
    JSpinner peopleSpinner;
    JTextField t5 = new JTextField(10);

    public UserReservationFrame() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        SpinnerDateModel timeModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.HOUR_OF_DAY);
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);

        SpinnerNumberModel peopleModel = new SpinnerNumberModel(1, 1, 20, 1);
        peopleSpinner = new JSpinner(peopleModel);

        setTitle("予約画面 - " + LoginFrame.login + "様");
        setSize(350, 300);
        setLocationRelativeTo(null);

        JPanel mainP = new JPanel(new GridLayout(6, 1));

        JPanel p1 = new JPanel();
        JLabel l1 = new JLabel("支店名");
        lordShowNames();
        p1.add(l1);
        p1.add(t1);
        mainP.add(p1);

        JPanel p2 = new JPanel();
        JLabel l2 = new JLabel("ご来店予定日");
        p2.add(l2);
        p2.add(t2);
        mainP.add(p2);

        JPanel p3 = new JPanel();
        JLabel l3 = new JLabel("ご来店時間");
        p3.add(l3);
        p3.add(timeSpinner);
        mainP.add(p3);

        JPanel p4 = new JPanel();
        JLabel l4 = new JLabel("ご来店予定人数");
        p4.add(l4);
        p4.add(peopleSpinner);
        mainP.add(p4);

        JPanel p5 = new JPanel();
        JLabel l5 = new JLabel("予約者様の電話番号");
        p5.add(l5);
        p5.add(t5);
        mainP.add(p5);

        JPanel p6 = new JPanel();
        JButton b1 = new JButton("戻る");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserMainFrame();
            }
        });

        JButton b2 = new JButton("クリア");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        JButton b3 = new JButton("予約する");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fieldCheck()) {
                    updateReservation();
                }
            }
        });

        p6.add(b1);
        p6.add(b2);
        p6.add(b3);
        mainP.add(p6);

        add(mainP);
        setVisible(true);
    }

    private void lordShowNames() {
        String sql = "SELECT shop_name FROM shop";

        try {
            PreparedStatement ps = DbConn.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                t1.addItem(rs.getString("shop_name"));
            }

            if (t1.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "現在登録されている店舗がありません。");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        t1.setSelectedIndex(-1);
        t2.setDate(null);
        peopleSpinner.setValue(1);
        t5.setText("");
    }

    private boolean fieldCheck() {
        boolean isError = false;

        if (t1.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "支店名を入力してください");
            isError = true;
        } else if (t2.getDate() == null) {
            JOptionPane.showMessageDialog(null, "ご来店予定日を選択してください");
            isError = true;
        } else if ((Integer) peopleSpinner.getValue() < 1 || (Integer) peopleSpinner.getValue() > 20) {
            JOptionPane.showMessageDialog(null, "ご来店予定人数は1から20までの間で指定してください");
            isError = true;
        } else if (t5.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "予約者様の電話番号を入力してください");
            isError = true;
        }

        return isError;
    }

    private void updateReservation() {
        String sql = "INSERT INTO reservation (shop_name, reservation_date, reservation_time, people_number, mobile_number, user_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = DbConn.conn.prepareStatement(sql);

            ps.setString(1, (String) t1.getSelectedItem());
            ps.setDate(2, new Date(t2.getDate().getTime()));
            ps.setTime(3, new Time(((java.util.Date) timeSpinner.getValue()).getTime()));
            ps.setInt(4, (Integer) peopleSpinner.getValue());
            ps.setString(5, t5.getText());
            ps.setString(6, LoginFrame.login);

            int result = ps.executeUpdate();

            if (result == 1) {
                JOptionPane.showMessageDialog(this, "予約が完了しました");
                dispose();
                new UserMainFrame();
            } else {
                JOptionPane.showMessageDialog(this, "予約の作成に失敗しました");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
