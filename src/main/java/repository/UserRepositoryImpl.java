package repository;

import game.Credentials;
import game.User;
import repository.db.config.DbConnector;
import repository.interfaces.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {
    private final DbConnector dataSource;
    private static final String INSERT_USERS_SQL = """
                INSERT INTO users (name, password) VALUES (?, ?)
            """;

    private static final String SELECT_USERS_SQL = """
                SELECT * FROM users WHERE name = ? AND password = ?
            """;

    private static final String SELECT_USER_BY_USERNAME_SQL = """
                SELECT * FROM users WHERE name = ?
            """;

    private static final String SETUP_TABLE = """
                CREATE TABLE IF NOT EXISTS Users (
                    name varchar(255),
                    password varchar(255) NOT NULL,
                    PRIMARY KEY (name)
                );
            """;

    public UserRepositoryImpl(DbConnector dataSource) {
        this.dataSource = dataSource;
        try (PreparedStatement ps = dataSource.getConnection()
                .prepareStatement(SETUP_TABLE)){
            ps.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to setup up table", e);
        }
    }

    @Override
    public void create(Credentials credentials) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(INSERT_USERS_SQL)) {
                ps.setString(1, credentials.getUsername());
                ps.setString(2, credentials.getPassword());
                ps.executeUpdate();
            }
        }catch (SQLException e) {
            throw new IllegalStateException("DB query failed", e);
        }
    }

    @Override
    public boolean checkCredentials(Credentials credentials) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_USERS_SQL)) {
                ps.setString(1, credentials.getUsername());
                ps.setString(2, credentials.getPassword());
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        }catch (SQLException e) {
            throw new IllegalStateException("DB query failed", e);
        }
        return false;
    }

    @Override
    public User findUserByUsername(String username) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_USER_BY_USERNAME_SQL)) {
                ps.setString(1, username);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return convertResultSetToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("DB query failed", e);
        }
        return null;
    }

    private static User convertResultSetToUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getString(1),
                resultSet.getString(2),
                500
        );
    }
}

