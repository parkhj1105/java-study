package admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import reservationsystem.LoginFrame;

public class AdminMainFrame extends JFrame{

	public AdminMainFrame() {
        setTitle("予約メイン画面 -" + LoginFrame.login + "様");
        setSize(500, 300);
        setLocationRelativeTo(null);
        JPanel mainP = new JPanel(new GridLayout(4, 1));

        JPanel p0 = new JPanel(new BorderLayout());

        JLabel l0 = new JLabel("ようこそ" + LoginFrame.login + "様");

        JPanel p1 = new JPanel();

        JButton b1 = new JButton("予約確認");
        b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				dispose();
				new AdminReservationCheck();
			}
		});

        JPanel p2 = new JPanel();

        JButton b2 = new JButton("新規店舗登録");
        b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				dispose();
				new AdminShopSignin();
			}
		});

        JPanel p4 = new JPanel(new BorderLayout());

        JButton b4 = new JButton("ログアウト");
        b4.setBackground(Color.pink);
        b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				dispose();
				new LoginFrame();
				LoginFrame.login = "";
			}
		});

        JButton b4_1 = new JButton("会員情報更新");
        b4_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				setVisible(false);
				new AdminUserinfoChange();
			}
		});

        p0.add(l0,BorderLayout.EAST);
        p1.add(b1);
        p2.add(b2);
        p4.add(b4,BorderLayout.WEST);
        p4.add(b4_1,BorderLayout.EAST);

        mainP.add(p0);
        mainP.add(p1);
        mainP.add(p2);
        mainP.add(p4);

        add(mainP);

        setVisible(true);

	}

}
