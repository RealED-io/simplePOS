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
    protected Connection getConnection(String hostName, String dbName, String user, String password) {
        try {
            String url = "jdbc:mysql://" + hostName + ":3306/" + dbName;
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Connection getConnection() {
        return getConnection(hostName, dbName, user, password);
    }

    protected void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected ResultSet query(String query) {
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
    protected ResultSet query(String query, String... params) {
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

    protected int save(String query, String... params) {
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int paramIndex = 1;
            for (String param : params) {
                statement.setString(paramIndex++, param);
            }
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) {
                int id = rs.getInt(1);
                rs.close();
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return 0;
    }

    protected int getLastInsertId() {
        String queryStatement = "SELECT LAST_INSERT_ID()";
        try {
            ResultSet rs = query(queryStatement);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    protected String toStringOrNULL(double number) {
        if(number == 0) return null;
        return String.valueOf(number);
    }

    protected String toStringOrNULL(int number) {
        if(number == 0) return null;
        return String.valueOf(number);
    }

    protected String toStringOrNULL(String string) {
        if(string == null) return null;
        return string;
    }
}
