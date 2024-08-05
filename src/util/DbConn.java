package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConn {

    public static Connection conn;

    public static void getConnection() {

//        String url = "jdbc:mysql://localhost:3306/resrvation";
        String url = "jdbc:h2:~/resrvation";

        String id = "root";

        String pw = "";

        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
