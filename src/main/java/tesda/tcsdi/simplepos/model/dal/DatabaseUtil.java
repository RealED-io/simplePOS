package tesda.tcsdi.simplepos.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static String hostName = "localhost";
    private static String dbName = "simplepos";
    private static String user = "root";
    private static String password = "MySQLpassword1234";

    public static Connection getConnection() {
        return getConnection(hostName, dbName, user, password);
    }

    /*
    * Generic getConnection method
    */
    public static Connection getConnection(String hostName, String dbName, String user, String password) {
        try {
            String url = "jdbc:mysql://" + hostName + ":3306/" + dbName;
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
