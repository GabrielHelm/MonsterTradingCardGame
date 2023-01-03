package repository.db.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection implements DbConnector{
    private final HikariDataSource ds;

    private DatabaseConnection() {
        HikariConfig config = new HikariConfig(
                "src/main/resources/hikari.properties"
        );
        ds = new HikariDataSource(config);
    }

    private static DatabaseConnection dataSource;

    public static DatabaseConnection getInstance() {
        if (dataSource == null) {
            dataSource = new DatabaseConnection();
        }
        return dataSource;
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            printSQLException(e);
            throw new IllegalStateException("Database not available!", e);
        }
    }
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
