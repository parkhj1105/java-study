package user;

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

import reservationsystem.LoginFrame;
import util.DbConn;
import vo.UserVo;

public class UserChangeFrame extends JFrame {

    private JTextField t1 = new JTextField(10); // ID
    private JTextField t2 = new JTextField(10); // 名前
    private JTextField t3 = new JTextField(10); // ログインID
    private JPasswordField t4 = new JPasswordField(10); // パスワード
    private JPasswordField t5 = new JPasswordField(10); // パスワード確認
    private JTextField t6 = new JTextField(10); // 携帯番号
    private JTextField t7 = new JTextField(10); // メールアドレス

    public UserChangeFrame() {
        setTitle("登録情報変更");
        setSize(350, 300);
        setLocationRelativeTo(null);

        UserVo userInfoVo = findUservo();

        JPanel mainP = new JPanel(new GridLayout(8, 1));

        JPanel p1 = new JPanel();
        JLabel l1 = new JLabel("ID");
        t1.setText(userInfoVo.getId());
        t1.setEditable(false); // ID는 수정 불가능

        p1.add(l1);
        p1.add(t1);

        JPanel p2 = new JPanel();
        JLabel l2 = new JLabel("お名前");
        t2.setText(userInfoVo.getUser_name());
        p2.add(l2);
        p2.add(t2);

        JPanel p3 = new JPanel();
        JLabel l3 = new JLabel("ログインID");
        t3.setText(userInfoVo.getUser_id());
        p3.add(l3);
        p3.add(t3);

        JPanel p4 = new JPanel();
        JLabel l4 = new JLabel("パスワード");
        p4.add(l4);
        p4.add(t4);

        JPanel p5 = new JPanel();
        JLabel l5 = new JLabel("パスワード確認");
        p5.add(l5);
        p5.add(t5);

        JPanel p6 = new JPanel();
        JLabel l6 = new JLabel("携帯番号");
        t6.setText(userInfoVo.getMoblie_number());
        p6.add(l6);
        p6.add(t6);

        JPanel p7 = new JPanel();
        JLabel l7 = new JLabel("メールアドレス");
        t7.setText(userInfoVo.getEmail());
        p7.add(l7);
        p7.add(t7);

        JPanel p8 = new JPanel();
        JButton b1 = new JButton("クリア");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        JButton b2 = new JButton("変更");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fieldCheck()) {
                    updateUserInfo();
                }
            }
        });

        JButton b3 = new JButton("戻り");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserMainFrame();
            }
        });

        p8.add(b1);
        p8.add(b2);
        p8.add(b3);

        mainP.add(p1);
        mainP.add(p2);
        mainP.add(p3);
        mainP.add(p4);
        mainP.add(p5);
        mainP.add(p6);
        mainP.add(p7);
        mainP.add(p8);

        add(mainP);

        setVisible(true);
    }

    private void clearFields() {
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");
        t6.setText("");
        t7.setText("");
    }

    private UserVo findUservo() {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        UserVo userVo = new UserVo();

        try (PreparedStatement ps = DbConn.conn.prepareStatement(sql)) {
            ps.setString(1, LoginFrame.login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userVo.setId(rs.getString("id"));
                userVo.setUser_id(rs.getString("user_id"));
                userVo.setUser_name(rs.getString("user_name"));
                userVo.setPassword(rs.getString("password"));
                userVo.setMoblie_number(rs.getString("mobile_number"));
                userVo.setEmail(rs.getString("email"));
                // Add user_type if needed
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userVo;
    }

    private boolean fieldCheck() {
        boolean isError = false;

        if (t1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "IDを入力してください");
            isError = true;
        } else if (new String(t4.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "パスワードを入力してください");
            isError = true;
        } else if (new String(t5.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "パスワード確認を入力してください");
            isError = true;
        } else if (!new String(t4.getPassword()).equals(new String(t5.getPassword()))) {
            JOptionPane.showMessageDialog(null, "パスワードが一致しません。");
            isError = true;
        } else if (t2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "名前を入力してください");
            isError = true;
        } else if (t3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ログインIDを入力してください");
            isError = true;
        } else if (t6.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "携帯番号を入力してください");
            isError = true;
        } else if (t7.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "メールアドレスを入力してください");
            isError = true;
        }
        return isError;
    }

    private void updateUserInfo() {
    	
        String sql = "UPDATE users SET password = ?, user_name = ?, user_id = ?, mobile_number = ?, email = ? WHERE id = ?";

        try (PreparedStatement ps = DbConn.conn.prepareStatement(sql)) {
            ps.setString(1, new String(t4.getPassword()));
            ps.setString(2, t2.getText());
            ps.setString(3, t3.getText());
            ps.setString(4, t6.getText());
            ps.setString(5, t7.getText());
            ps.setString(6, t1.getText());

            int result = ps.executeUpdate();

            if (result == 1) {
                JOptionPane.showMessageDialog(null, "更新完了");
                new UserMainFrame();
            } else {
                JOptionPane.showMessageDialog(null, "更新失敗");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
