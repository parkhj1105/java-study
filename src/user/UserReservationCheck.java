package user;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import reservationsystem.LoginFrame;
import util.DbConn;

public class UserReservationCheck extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public UserReservationCheck() {
        setTitle("予約確認画面");
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] columnNames = {"コース名", "ご来店予定日", "ご来店時間", "ご来店予定人数", "予約者様の電話番号"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        loadReservationData();

        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        JButton b1 = new JButton("戻る");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserMainFrame();
            }
        });

        JPanel p1 = new JPanel();
        p1.add(b1);
        add(p1, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadReservationData() {
        String sql = "SELECT shop_name, reservation_date, reservation_time, people_number, mobile_number " +
                     "FROM reservation WHERE user_id = ?";

        try {
            PreparedStatement ps = DbConn.conn.prepareStatement(sql);
            ps.setString(1, LoginFrame.login);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String shopName = rs.getString("shop_name");
                Date reservationDate = rs.getDate("reservation_date");
                Time reservationTime = rs.getTime("reservation_time");
                int peopleNumber = rs.getInt("people_number");
                String mobileNumber = rs.getString("mobile_number");

                // Add data to the table model
                tableModel.addRow(new Object[]{
                    shopName,
                    reservationDate.toString(),
                    reservationTime.toString(),
                    peopleNumber,
                    mobileNumber
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
