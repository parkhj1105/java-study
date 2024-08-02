package reservationsystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import admin.AdminSigninFrame;
import user.UserSigninFrame;

public class SigninFrame extends JFrame{

	public SigninFrame() {

		setTitle("会員登録");
		setSize(270, 150);
		setLocationRelativeTo(null);

		JPanel mainP = new JPanel(new GridLayout(2 , 1));

		JPanel p1 = new JPanel();
		JButton b1 = new JButton("お客様");
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				dispose();
				new UserSigninFrame();
			}
		});
		JButton b2 = new JButton("店主様");
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				dispose();
				new AdminSigninFrame();
			}
		});

		p1.add(b1);
		p1.add(b2);

		JPanel p2 = new JPanel();

		JButton b3 = new JButton("ログイン場面に戻る");
		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				new LoginFrame();
				setVisible(false);
			}
		});

		p2.add(b3);

		mainP.add(p1);
		mainP.add(p2);

		add(mainP);

		setVisible(true);

	}

}
