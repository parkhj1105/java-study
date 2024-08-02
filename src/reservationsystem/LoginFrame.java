package reservationsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import admin.AdminMainFrame;
import user.UserMainFrame;
import util.DbConn;

public class LoginFrame extends JFrame {

    public static String login;

    public LoginFrame() {
        DbConn.getConnection();
        setTitle("JISA予約");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel mainP = new JPanel(new GridLayout(4, 1));

        JPanel p0 = new JPanel();
        JLabel l0 = new JLabel("お悩み解決！！JISA予約★");
        l0.setFont(new Font("MS Gothic", Font.BOLD, 25));
        l0.setForeground(new Color(0, 0, 255));
        p0.add(l0);
        mainP.add(p0);

        JPanel p1 = new JPanel();
        JLabel l1 = new JLabel("会員ID");
        JTextField t1 = new JTextField(17);
        p1.add(l1);
        p1.add(t1);
        mainP.add(p1);

        JPanel p2 = new JPanel();
        JLabel l2 = new JLabel("会員PW");
        JPasswordField t2 = new JPasswordField(10);
        JButton b1 = new JButton("ログイン");
        p2.add(l2);
        p2.add(t2);
        p2.add(b1);
        mainP.add(p2);

        JPanel p3 = new JPanel();
        JButton b2 = new JButton("会員登録");

        ActionListener loginAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = t1.getText();
                String pw = new String(t2.getPassword());
                LoginCheck(id, pw);
            }
        };

        b1.addActionListener(loginAction);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SigninFrame();
            }
        });

        t1.addActionListener(loginAction);
        t2.addActionListener(loginAction);

        p3.add(b2);
        mainP.add(p3);

        add(mainP);
        setVisible(true);
    }

    public void LoginCheck(String id, String pw) {
        String sql = "SELECT user_type FROM users WHERE user_id = ? AND password = ?";
        try {
            PreparedStatement ps = DbConn.conn.prepareStatement(sql);
            login = id;
            ps.setString(1, id);
            ps.setString(2, pw);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String userType = rs.getString("user_type");

                if ("admin".equals(userType)) {
                    JOptionPane.showMessageDialog(null, "ログインします！");
                    dispose();
                    new AdminMainFrame();

                } else if ("user".equals(userType)) {
                    JOptionPane.showMessageDialog(null, "ログインします！");
                    dispose();
                    new UserMainFrame();

                } else {
                    JOptionPane.showMessageDialog(null, "情報が見つかりません。");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
