package tesda.tcsdi.simplepos.model.dal;

import java.sql.*;

public class DatabaseUtil {
    private String hostName = "localhost";
    private String dbName = "simplepos";
    private String user = "root";
    private String password = "MySQLpassword1234";

    /*
     * Generic getConnection method
     */
    public Connection getConnection(String hostName, String dbName, String user, String password) {
        try {
            String url = "jdbc:mysql://" + hostName + ":3306/" + dbName;
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return getConnection(hostName, dbName, user, password);
    }

    /**
     *
     * @param query sql query where '?' are used as placeholders for params
     * @param params value for the '?'s in query string
     * @return ResultSet from query, returns null if none was queried
     */
    public ResultSet query(String query, String... params) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            int paramIndex = 1;
            for (String param : params) {
                statement.setString(paramIndex++, param);
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
