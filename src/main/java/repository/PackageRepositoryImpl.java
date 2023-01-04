package repository;

import game.User;
import game.card.CardCollection;
import repository.db.config.DbConnector;
import repository.interfaces.PackageRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PackageRepositoryImpl implements PackageRepository {

    private final DbConnector dataSource;

    private static final String INSERT_PACKAGE_SQL = """
                INSERT INTO packages (u_id, card1id, card2id, card3id, card4id, card5id) VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_RANDOM_PACKAGE_SQL = """
                SELECT card1id, card2id, card3id, card4id, card5id FROM packages WHERE u_id = (
                    SELECT u_id FROM packages ORDER BY RANDOM() LIMIT 1 )
            """;

    private static final String SETUP_TABLE = """
                CREATE TABLE IF NOT EXISTS packages (
                    u_id varchar(255),
                    card1id varchar(255),
                    card2id varchar(255),
                    card3id varchar(255),
                    card4id varchar(255),
                    card5id varchar(255),
                    PRIMARY KEY (u_id),
                    FOREIGN KEY (card1id) REFERENCES Cards (id),
                    FOREIGN KEY (card2id) REFERENCES Cards (id),
                    FOREIGN KEY (card3id) REFERENCES Cards (id),
                    FOREIGN KEY (card4id) REFERENCES Cards (id),
                    FOREIGN KEY (card5id) REFERENCES Cards (id)
                );
            """;

    public PackageRepositoryImpl(DbConnector dataSource) {
        this.dataSource = dataSource;
        try (PreparedStatement ps = dataSource.getConnection()
                .prepareStatement(SETUP_TABLE)){
            ps.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to setup up table", e);
        }
    }

    @Override
    public void createPackage(CardCollection cardCollection) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(INSERT_PACKAGE_SQL)) {
                ps.setString(1, cardCollection.getU_ID());
                for (int i = 2; i < 7; i++) {
                    ps.setString(i, cardCollection.getCardFromCollection(i-2).getId());
                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create package", e);
        }
    }

    @Override
    public List<String> getCardIdsFromRandomPackage() {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_RANDOM_PACKAGE_SQL)) {
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return convertResultSetToCardIdsList(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get Card Ids from Random Package", e);
        }
        return Collections.emptyList();
    }

    private static List<String> convertResultSetToCardIdsList(ResultSet resultSet) throws SQLException {
        List<String> cardIds = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            cardIds.add(resultSet.getString(i));
        }
        return cardIds;
    }
}

