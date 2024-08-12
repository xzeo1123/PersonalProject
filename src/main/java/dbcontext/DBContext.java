package dbcontext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    public Connection getConnection() throws Exception {
        String serverName = "localhost";
        String dbName = "Order";
        String portNumber = "3306";
        String userID = "root";
        String password = "";

        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName + "?useUnicode=true&characterEncoding=UTF-8";
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(url, userID, password);
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(new DBContext().getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
