package repository;

import repository.db.config.DbConnector;
import repository.interfaces.UserCardsRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCardsRepositoryImpl implements UserCardsRepository {

    private final DbConnector dataSource;

    private static final String INSERT_CARD_FOR_USER_SQL = """
                INSERT INTO user_cards (username, cardId, deck) VALUES (?, ?, ?)
            """;

    private static final String SELECT_CARDS_FROM_USER_SQL = """
                SELECT cardId FROM user_cards WHERE username = ?
            """;

    private static final String SELECT_CARDS_FROM_USER_DECK_SQL = """
                SELECT cardId FROM user_cards WHERE username = ? AND deck = true
            """;

    private static final String UPDATE_CARDS_CHANGE_TO_USER_DECK_SQL = """
                UPDATE user_cards SET deck = true WHERE username = ? AND cardID = ?
            """;

    private static final String SETUP_TABLE = """
                CREATE TABLE IF NOT EXISTS user_cards (
                    username varchar(255),
                    cardId varchar(255),
                    deck boolean,
                    PRIMARY KEY (username, cardId),
                    FOREIGN KEY (username) REFERENCES Users (name),
                    FOREIGN KEY (cardId) REFERENCES cards (id)
                );
            """;

    public UserCardsRepositoryImpl(DbConnector dataSource) {
        this.dataSource = dataSource;
        try (PreparedStatement ps = dataSource.getConnection()
                .prepareStatement(SETUP_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to setup up table", e);
        }
    }

    @Override
    public void addCardToUserCards(String cardId, String username) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(INSERT_CARD_FOR_USER_SQL)) {
                ps.setString(1, username);
                ps.setString(2, cardId);
                ps.setBoolean(3, false);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to add card to user stack", e);
        }
    }

    @Override
    public void updateCardChangeToDeck(String cardId, String username) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(UPDATE_CARDS_CHANGE_TO_USER_DECK_SQL)) {
                ps.setString(1, username);
                ps.setString(2, cardId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed update card", e);
        }
    }

    @Override
    public List<String> getAllCardIdsFromUserCards(String username) {
        return getAllCardIds(username, SELECT_CARDS_FROM_USER_SQL);
    }

    @Override
    public List<String> getAllCardIdsFromUserDeck(String username) {
        return getAllCardIds(username, SELECT_CARDS_FROM_USER_DECK_SQL);
    }

    private List<String> getAllCardIds(String username, String selectCardsFromUserDeckSql) {
        List<String> cardIds = new ArrayList<>();
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(selectCardsFromUserDeckSql)) {
                ps.setString(1, username);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    cardIds.add(resultSet.getString(1));
                }
                return cardIds;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get cardIds", e);
        }
    }
}
