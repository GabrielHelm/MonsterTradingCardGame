package repository;

import game.UserProfile;
import game.UserStats;
import repository.db.config.DbConnector;
import repository.interfaces.UserProfileRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserProfileRepositoryImpl implements UserProfileRepository {
    private final DbConnector dataSource;

    private static final String INSERT_PROFILE_FOR_USER_SQL = """
                INSERT INTO user_profile (username, name, bio, image, elo, wins, losses) VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_PROFILE_FROM_USER_SQL = """
                SELECT name, bio, image FROM user_profile WHERE username = ?
            """;
    private static final String SELECT_STATS_FROM_USER_SQL = """
                SELECT name, elo, wins, losses FROM user_profile WHERE username = ?
            """;
    private static final String SELECT_ALL_STATS_SQL = """
                SELECT name, elo, wins, losses FROM user_profile ORDER BY elo DESC
            """;

    private static final String UPDATE_PROFILE_FROM_USER_SQL = """
                UPDATE user_profile SET name = ?, bio = ?, image = ? WHERE username = ?
            """;

    private static final String SETUP_TABLE = """
                CREATE TABLE IF NOT EXISTS user_profile (
                    username varchar(255),
                    name varchar(255),
                    bio varchar(255),
                    image varchar(255),
                    elo int,
                    wins int,
                    losses int,
                    PRIMARY KEY (username),
                    FOREIGN KEY (username) REFERENCES Users (name)
                );
            """;

    public UserProfileRepositoryImpl(DbConnector dataSource) {
        this.dataSource = dataSource;
        try (PreparedStatement ps = dataSource.getConnection()
                .prepareStatement(SETUP_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to setup up table", e);
        }
    }

    @Override
    public void addUserProfile(String username, UserProfile userProfile) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(INSERT_PROFILE_FOR_USER_SQL)) {
                ps.setString(1, username);
                ps.setString(2, userProfile.getName());
                ps.setString(3, userProfile.getBio());
                ps.setString(4, userProfile.getImage());
                ps.setInt(5, 100);
                ps.setInt(6, 0);
                ps.setInt(7, 0);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to add user profile", e);
        }
    }

    @Override
    public void updateUserProfile(String username, UserProfile userProfile) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(UPDATE_PROFILE_FROM_USER_SQL)) {
                ps.setString(1, userProfile.getName());
                ps.setString(2, userProfile.getBio());
                ps.setString(3, userProfile.getImage());
                ps.setString(4, username);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update user profile", e);
        }
    }

    @Override
    public UserProfile getUserProfile(String username) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_PROFILE_FROM_USER_SQL)) {
                ps.setString(1, username);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return convertResultSetToUserProfile(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get user profile", e);
        }
        return null;
    }

    private static UserProfile convertResultSetToUserProfile(ResultSet resultSet) throws SQLException {
        return new UserProfile(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3)
        );
    }

    @Override
    public UserStats getStats(String username) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_STATS_FROM_USER_SQL)) {
                ps.setString(1, username);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return convertResultSetToUserStats(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get user profile", e);
        }
        return null;
    }

    @Override
    public List<UserStats> getScoreboard() {
        List<UserStats> userStats = new ArrayList<>();
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_ALL_STATS_SQL)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    userStats.add(convertResultSetToUserStats(resultSet));
                }
                return userStats;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get user profile", e);
        }
    }

    private static UserStats convertResultSetToUserStats(ResultSet resultSet) throws SQLException {
        return new UserStats(
                resultSet.getString(1),
                resultSet.getInt(2),
                resultSet.getInt(3),
                resultSet.getInt(4)
        );
    }
}
