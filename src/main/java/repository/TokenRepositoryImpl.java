package repository;

import game.Token;
import game.User;
import repository.db.config.DbConnector;
import repository.interfaces.TokenRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenRepositoryImpl implements TokenRepository {

    private final DbConnector dataSource;

    private static final String INSERT_TOKEN_SQL = """
                INSERT INTO auth_token (name, token, valid_until) VALUES (?, ?, ?)
            """;

    private static final String SELECT_TOKEN_FROM_TOKENNAME_SQL = """
                SELECT token, valid_until FROM auth_token WHERE token = ?
            """;

    private static final String SELECT_USERNAME_FROM_TOKENNAME_SQL = """
                SELECT name FROM auth_token WHERE token = ?
            """;

    private static final String UPDATE_TIMESTAMP_FROM_TOKEN_SQL = """
                UPDATE auth_token SET valid_until = ? WHERE token = ?;
            """;

    private static final String SETUP_TABLE = """
                CREATE TABLE IF NOT EXISTS auth_token (
                    Name varchar(255),
                    Token varchar(255),
                    valid_until timestamp NOT NULL,
                    PRIMARY KEY (Name),
                    FOREIGN KEY (Name) REFERENCES Users (Name)
                );
            """;

    public TokenRepositoryImpl(DbConnector dataSource) {
        this.dataSource = dataSource;
        try (PreparedStatement ps = dataSource.getConnection()
                .prepareStatement(SETUP_TABLE)){
            ps.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to setup up table", e);
        }
    }

    @Override
    public void createTokenForUser(User user) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(INSERT_TOKEN_SQL)) {
                user.generateToken();
                ps.setString(1, user.getCredentials().getUsername());
                ps.setString(2, user.getToken().getName());
                ps.setTimestamp(3, user.getToken().getValid_until());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create token", e);
        }
    }

    @Override
    public Token getTokenFromTokenName(String tokenName) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_TOKEN_FROM_TOKENNAME_SQL)) {
                ps.setString(1, tokenName);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return convertResultSetToToken(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get token", e);
        }
        return null;
    }

    private static Token convertResultSetToToken(ResultSet resultSet) throws SQLException {
        return new Token(
                resultSet.getString(1),
                resultSet.getTimestamp(2)
        );
    }

    @Override
    public String getUsernameFromTokenName(String tokenName) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_USERNAME_FROM_TOKENNAME_SQL)) {
                ps.setString(1, tokenName);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get username", e);
        }
        return null;
    }

    @Override
    public void updateTokenTimestamp(Token token) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(UPDATE_TIMESTAMP_FROM_TOKEN_SQL)) {
                token.updateToken();
                ps.setTimestamp(1, token.getValid_until());
                ps.setString(2, token.getName());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update token", e);
        }
    }

}
