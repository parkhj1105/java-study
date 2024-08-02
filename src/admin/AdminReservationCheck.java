package admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import reservationsystem.LoginFrame;
import util.DbConn;

public class AdminReservationCheck extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> shopDropdown;

    static final String ALL = "全て";

    public AdminReservationCheck() {
        setTitle("予約確認画面");
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] columnNames = {"予約ID", "予約日", "予約時間", "人数", "電話番号", "ショップ名"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JPanel inputPanel = new JPanel();
        shopDropdown = new JComboBox<>();
        JButton searchButton = new JButton("検索");

        inputPanel.add(shopDropdown);
        inputPanel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadReservationData();
            }
        });

        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);

        JButton backButton = new JButton("戻る");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminMainFrame();
            }
        });

        JPanel p1 = new JPanel();
        p1.add(backButton);
        add(p1, BorderLayout.SOUTH);

        loadShopList();

        setVisible(true);
    }

    private void loadShopList() {
        String adminId = LoginFrame.login;
        String sql = "SELECT shop_name FROM shop WHERE admin_id = ?";

        try (PreparedStatement ps = DbConn.conn.prepareStatement(sql)) {
            ps.setString(1, adminId);

            ResultSet rs = ps.executeQuery();
            List<String> shopNames = new ArrayList<>();
            shopNames.add("全て"); // 모든 예약을 표시하는 옵션

            while (rs.next()) {
                shopNames.add(rs.getString("shop_name"));
            }
            shopDropdown.setModel(new DefaultComboBoxModel<String>(shopNames.toArray(new String[0])));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadReservationData() {

        String adminId = LoginFrame.login;
        String selectedShop = (String) shopDropdown.getSelectedItem();

        StringBuffer sql = new StringBuffer();

        sql.append("SELECT r.id, r.reservation_date, r.reservation_time, r.people_number, r.mobile_number, r.shop_name FROM reservation r JOIN shop s ON r.shop_name = s.shop_name WHERE s.admin_id = ? ");
        if (!ALL.equals(selectedShop)) {
        	sql.append("AND r.shop_name = ?");
        }

        try (PreparedStatement ps = DbConn.conn.prepareStatement(sql.toString())) {
            ps.setString(1, adminId);
            if (!ALL.equals(selectedShop)) {
                ps.setString(2, selectedShop);
            }

            try (ResultSet rs = ps.executeQuery()) {
                tableModel.setRowCount(0);
                boolean hasData = false;
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getDate("reservation_date").toString(),
                        rs.getTime("reservation_time").toString(),
                        rs.getInt("people_number"),
                        rs.getString("mobile_number"),
                        rs.getString("shop_name")
                    });
                    hasData = true;
                }
                if (!hasData) {
                    tableModel.addRow(new Object[]{"", "", "", "", "", ""});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

