package tesda.tcsdi.simplepos.model.dal;

import java.sql.*;

public class DatabaseUtil {
    private String hostName = "localhost";
    private String dbName = "simplepos";
    private String user = "root";
    private String password = "MySQLpassword1234";

    private Connection connection;
    /*
     * Generic getConnection method
     */
    public Connection getConnection(String hostName, String dbName, String user, String password) {
        try {
            String url = "jdbc:mysql://" + hostName + ":3306/" + dbName;
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnection() {
        return getConnection(hostName, dbName, user, password);
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String query) {
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param query preparedStatement statement
     * @param params parameters to be replaced by '?' placeholders on @param query
     * @return ResultSet from query, returns null if none was queried
     */
    public ResultSet query(String query, String... params) {
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            int paramIndex = 1;
            for (String param : params) {
                statement.setString(paramIndex++, param);
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
