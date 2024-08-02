package user;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import reservationsystem.LoginFrame;
import util.DbConn;

public class UserSigninFrame extends JFrame{
	public static String email;
	public UserSigninFrame() {

		setTitle("会員登録");
		setSize(350, 300);
		setLocationRelativeTo(null);

		JPanel mainP = new JPanel(new GridLayout(7 , 1));

		JPanel p1 = new JPanel();
		JLabel l1 = new JLabel("お名前");
		JTextField t1 = new JTextField(10);

		p1.add(l1);
		p1.add(t1);
		mainP.add(p1);

		JPanel p2 = new JPanel();
		JLabel l2 = new JLabel("ログインID");
		JTextField t2 = new JTextField(10);

		p2.add(l2);
		p2.add(t2);
		mainP.add(p2);

		JPanel p3 = new JPanel();
		JLabel l3 = new JLabel("パスワード");
		JPasswordField t3 = new JPasswordField(10);

		p3.add(l3);
		p3.add(t3);
		mainP.add(p3);

		JPanel p4 = new JPanel();
		JLabel l4 = new JLabel("パスワード確認");
		JPasswordField t4 = new JPasswordField(10);

		p4.add(l4);
		p4.add(t4);
		mainP.add(p4);

		JPanel p5 = new JPanel();
		JLabel l5 = new JLabel("携帯番号");
		JTextField t5 = new JTextField(10);

		p5.add(l5);
		p5.add(t5);
		mainP.add(p5);

		JPanel p6 = new JPanel();
		JLabel l6 = new JLabel("メールアドレス");
		JTextField t6 = new JTextField(10);

		p6.add(l6);
		p6.add(t6);
		mainP.add(p6);

		JPanel p7 = new JPanel();
		JButton b1 = new JButton("戻る");
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				dispose();
				new UserSigninFrame();
			}
		});
		JButton b2 = new JButton("登録");
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動生成されたメソッド・スタブ

				String name = t1.getText();
				String id = t2.getText();
				String pw1 = new String (t3.getPassword());
				String pw2 = new String (t4.getPassword());
				String mn = t5.getText();
				String em = t6.getText();

				boolean isError = false;

				if(name.isEmpty() || id.isEmpty() || pw1.isEmpty() || pw2.isEmpty() ||
						mn.isEmpty() || em.isEmpty()) {
					isError = true;
					JOptionPane.showMessageDialog(null, "入力が認識されていません。");
				}

				String EMAIL_PATTERN = "^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}+$";
				Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		        Matcher matcher = pattern.matcher(em);
		        if(!matcher.matches()) {
					isError = true;
		        	JOptionPane.showMessageDialog(null, "error");
		        }else {
		        	email = em;
		        }

				if(!pw1.equals(pw2)) {
					isError = true;
					JOptionPane.showMessageDialog(null, "パスワードを確認してください。");
				}

				if(!isError) {
					checkSignIn(name, id, pw1, mn, em);
				}
			}
		});

		p7.add(b1);
		p7.add(b2);
		mainP.add(p7);

		add(mainP);

		setVisible(true);

	}

	public void checkSignIn(String name, String userid, String password, String mn, String em) {

		String sql = "insert into users (user_name, user_id, password, mobile_number, email, user_type) values (?, ?, ?, ?, ?, 'user')";

		try {
			PreparedStatement ps = DbConn.conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, userid);
			ps.setString(3, password);
			ps.setString(4, mn);
			ps.setString(5, em);

			int result = ps.executeUpdate();

			if (result == 1) {

				JOptionPane.showMessageDialog(null, "会員登録成功！");
				dispose();
				emailCheck(em);
				new LoginFrame();
			}else {
				JOptionPane.showMessageDialog(null, "会員登録失敗...");
			}

		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void emailCheck(String email) {

	        String host = "smtp.gmail.com";

	        final String username = "testaka240802@gmail.com";
	        final String password = "zdmk zhvr ncol qlwp";

	        String toEmail = email;

	        Properties props = new Properties();

	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", "587");


	        Session session = Session.getInstance(props,
	            new Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });

	        try {

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	            message.setSubject("会員登録ありがとうございました！");
	            message.setText("会員登録が成功的に行われました！" + "\n" +
	            				"ありがとうございます！");


	            Transport.send(message);

	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	    }

	}
