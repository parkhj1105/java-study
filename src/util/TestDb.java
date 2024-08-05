package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

public class TestDb {

	public static void main(String[] args) throws SQLException {
        String jdbcUrl = "jdbc:h2:~/testdb";
        String username = "sa";
        String password = "";

        Connection connection = null;
        Statement statement = null;

        Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
        System.out.println("http://localhost:8082");

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            statement = connection.createStatement();

            // 테이블 존재 여부 확인
            ResultSet rs = connection.getMetaData().getTables(null, null, "USERINFO", null);
            if (!rs.next()) {
                // 테이블이 존재하지 않으면 생성
                String createTableSQL = "CREATE TABLE IF NOT EXISTS userinfo (id VARCHAR(20) PRIMARY KEY, password VARCHAR(255))";
                statement.execute(createTableSQL);
                System.out.println("table created successfully");
            } else {
                System.out.println("table already exists");
            }

            // 데이터 삽입 예제
//            String insertDataSQL = "INSERT INTO userinfo (id, password) VALUES ('john2', 'password12345')";
//            statement.execute(insertDataSQL);

            System.out.println("data insert successfully");

            // 데이터 조회 예제
            String sql = "select * from userinfo";

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("id: " + resultSet.getString("id"));
                System.out.println("password: " + resultSet.getString("password"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
