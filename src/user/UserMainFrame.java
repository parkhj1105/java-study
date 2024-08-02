package user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import reservationsystem.LoginFrame;
import util.DbConn;

public class UserMainFrame extends JFrame {

    public UserMainFrame() {
        setTitle("予約メイン画面 - " + LoginFrame.login + "様");
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel mainP = new JPanel(new GridLayout(5, 1));

        JPanel p0 = new JPanel(new BorderLayout());
        JLabel l0 = new JLabel("ようこそ " + LoginFrame.login + " 様");
        p0.add(l0, BorderLayout.EAST);

        JPanel p1 = new JPanel();
        JButton b1 = new JButton("予約確認");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserReservationCheck();
            }
        });
        p1.add(b1);

        JPanel p2 = new JPanel();
        JButton b2 = new JButton("新規予約");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserReservationFrame();
            }
        });
        p2.add(b2);

        JPanel p3 = new JPanel();
        JButton b3 = new JButton("予約キャンセル");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAllReservations();
            }
        });
        p3.add(b3);

        JPanel p4 = new JPanel(new BorderLayout());
        JButton b4 = new JButton("ログアウト");
        b4.setBackground(Color.pink);
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame();
                LoginFrame.login = "";
            }
        });
        p4.add(b4, BorderLayout.WEST);

        JButton b4_1 = new JButton("会員情報更新");
        b4_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserChangeFrame();
                dispose();
            }
        });
        p4.add(b4_1, BorderLayout.EAST);

        mainP.add(p0);
        mainP.add(p1);
        mainP.add(p2);
        mainP.add(p3);
        mainP.add(p4);

        add(mainP);
        setVisible(true);
    }

    private void deleteAllReservations() {
        String sql = "DELETE FROM reservation WHERE user_id = ?";
        try {
            PreparedStatement ps = DbConn.conn.prepareStatement(sql);
            ps.setString(1, LoginFrame.login);
            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "すべての予約が削除されました。");
            } else {
                JOptionPane.showMessageDialog(null, "削除する予約がありません。");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "予約の削除に失敗しました。");
        }
    }

}
