package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnectionWrapper {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";
    private static final String USER = "root";
    private static final String PASSWORD = "@CicaNica0203";
    private static final int TIMEOUT = 5;

    private Connection connection;

    public JDBCConnectionWrapper(String schema) {
        try {
            Class.forName(JDBC_DRIVER);  // pas1 -> cartela sim in tel
            connection = DriverManager.getConnection(DB_URL + schema, USER, PASSWORD);  // apelam nr si stabilim conexiune
            createTables();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS book(" +
                " id bigint NOT NULL AUTO_INCREMENT," +
                " author VARCHAR(500) NOT NULL," +
                " title VARCHAR(500) NOT NULL," +
                " publishedDate datetime DEFAULT NULL," +
                " price DECIMAL(5, 2) NOT NULL," +
                " stock INT NOT NULL," +
                " PRIMARY KEY(id)," +
                " UNIQUE KEY id_UNIQUE(id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
        statement.execute(sql);
    }

    public boolean testConnection() throws SQLException {
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection() {
        return connection;
    }
}
