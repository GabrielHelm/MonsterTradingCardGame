package repository;

import game.User;
import game.card.CardCollection;
import repository.db.config.DbConnector;
import repository.interfaces.PackageRepository;
import repository.interfaces.TokenRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PackageRepositoryImpl implements PackageRepository {

    private final DbConnector dataSource;

    private static final String INSERT_PACKAGE_SQL = """
                INSERT INTO packages (u_ID, card1Id, card2Id, card3Id, card4Id, card5Id) VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String SETUP_TABLE = """
                CREATE TABLE IF NOT EXISTS packages (
                    u_ID varchar(255),
                    card1Id varchar(255),
                    card2Id varchar(255),
                    card3Id varchar(255),
                    card4Id varchar(255),
                    card5Id varchar(255),
                    PRIMARY KEY (u_ID)
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
                    ps.setString(i, cardCollection.getCardFromCollection(i).getId());
                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create package", e);
        }
    }
}
