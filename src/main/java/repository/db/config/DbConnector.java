package repository.db.config;

import java.sql.Connection;

public interface DbConnector {
    Connection getConnection();
}
