package admin;

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
import javax.swing.JTextField;

import reservationsystem.LoginFrame;
import util.DbConn;

public class AdminShopSignin extends JFrame {

    public AdminShopSignin() {
        setTitle("ショップ登録");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel mainP = new JPanel(new GridLayout(5, 1));

        JPanel p2 = new JPanel();
        JLabel l2 = new JLabel("ショップ名");
        JTextField t2 = new JTextField(10);
        p2.add(l2);
        p2.add(t2);
        mainP.add(p2);

        JPanel p3 = new JPanel();
        JLabel l3 = new JLabel("ショップ所在地");
        JTextField t3 = new JTextField(10);
        p3.add(l3);
        p3.add(t3);
        mainP.add(p3);

        JPanel p4 = new JPanel();
        JLabel l4 = new JLabel("電話番号");
        JTextField t4 = new JTextField(10);
        p4.add(l4);
        p4.add(t4);
        mainP.add(p4);

        JPanel p5 = new JPanel();
        JLabel l5 = new JLabel("メールアドレス");
        JTextField t5 = new JTextField(10);
        p5.add(l5);
        p5.add(t5);
        mainP.add(p5);

        JPanel p6 = new JPanel();
        JButton b1 = new JButton("戻る");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminMainFrame();
            }
        });
        JButton b2 = new JButton("登録");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String na = t2.getText();
                String lo = t3.getText();
                String pn = t4.getText();
                String se = t5.getText();

                boolean isError = false;

                if (na.isEmpty() || lo.isEmpty() || pn.isEmpty() || se.isEmpty()) {
                    isError = true;
                    JOptionPane.showMessageDialog(null, "入力が認識されていません。");
                }

                if (!isError) {
                    checkShopRegistration(na, lo, pn, se);
                }
            }
        });
        p6.add(b1);
        p6.add(b2);
        mainP.add(p6);

        add(mainP);
        setVisible(true);
    }

    public void checkShopRegistration(String shopName, String shopLocation, String shopPhoneNumber, String shopEmail) {

    	String sql = "INSERT INTO shop (shop_name, shop_location, shop_phone_number, shop_email, admin_id) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = DbConn.conn.prepareStatement(sql);
            ps.setString(1, shopName);
            ps.setString(2, shopLocation);
            ps.setString(3, shopPhoneNumber);
            ps.setString(4, shopEmail);
            ps.setString(5, LoginFrame.login);

            int result = ps.executeUpdate();

            if (result == 1) {
                JOptionPane.showMessageDialog(null, "店舗登録成功！");
                dispose();
                new AdminMainFrame();
            } else {
                JOptionPane.showMessageDialog(null, "店舗登録失敗...");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
