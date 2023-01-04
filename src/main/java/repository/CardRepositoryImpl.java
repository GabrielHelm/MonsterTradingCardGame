package repository;

import game.Token;
import game.card.Card;
import game.card.CardType;
import game.card.ElementType;
import repository.db.config.DbConnector;
import repository.interfaces.CardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardRepositoryImpl implements CardRepository {

    private final DbConnector dataSource;

    private static final String INSERT_CARD_SQL = """
                INSERT INTO cards (id, name, damage, element, cardType) VALUES (?, ?, ?, ?, ?)
            """;

    private static final String SELECT_CARD_SQL = """
                SELECT name, damage , element, id, cardType FROM cards WHERE id = ?
            """;
    private static final String SETUP_TABLE = """
                CREATE TABLE IF NOT EXISTS cards (
                    id varchar(255),
                    name varchar(255),
                    damage double precision,
                    element varchar(255),
                    cardType varchar(255),
                    PRIMARY KEY (id)
                );
            """;

    public CardRepositoryImpl(DbConnector dataSource) {
        this.dataSource = dataSource;
        try (PreparedStatement ps = dataSource.getConnection()
                .prepareStatement(SETUP_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to setup up table", e);
        }
    }

    @Override
    public void createCard(Card card) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(INSERT_CARD_SQL)) {
                ps.setString(1, card.getId());
                ps.setString(2, card.getName());
                ps.setDouble(3, card.getDamage());
                ps.setString(4, card.getElement().toString());
                ps.setString(5, card.getCardType().toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create card", e);
        }
    }

    @Override
    public Card getCard(String id) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_CARD_SQL)) {
                ps.setString(1, id);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return convertResultSetToCard(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get card", e);
        }
        return null;
    }

    private Card convertResultSetToCard(ResultSet resultSet) throws SQLException {
        return new Card(
                resultSet.getString(1),
                resultSet.getDouble(2),
                ElementType.valueOf(resultSet.getString(3)),
                resultSet.getString(4),
                CardType.valueOf(resultSet.getString(5))
        );
    }
}
