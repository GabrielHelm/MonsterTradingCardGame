package repository;

import game.ParsingClasses.TradingDeal;
import repository.db.config.DbConnector;
import repository.interfaces.TradingRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradingRepositoryImpl implements TradingRepository {
    private final DbConnector dataSource;

    private static final String INSERT_TRADING_DEAL_SQL = """
                INSERT INTO tradingdeals (id, name, cardId, minimumDamage, cardType) VALUES (?, ?, ?, ?, ?)
            """;
    private static final String SELECT_TRADING_DEAL_SQL = """
                SELECT id, cardId, cardType, minimumDamage FROM tradingdeals WHERE id = ?
            """;

    private static final String SELECT_TRADING_DEAL_OWNER_SQL = """
                SELECT name FROM tradingdeals WHERE id = ?
            """;
    private static final String SELECT_ALL_TRADING_DEALS_FOR_USER_SQL = """
                SELECT id, cardId, cardType, minimumDamage FROM tradingdeals WHERE name != ?
            """;

    private static final String SELECT_ALL_TRADING_DEAL_IDS_FROM_USER_SQL = """
                SELECT id FROM tradingdeals WHERE name = ?
            """;

    private static final String DELETE_TRADING_DEAL = """
                DELETE FROM tradingdeals WHERE id = ?
            """;

    private static final String SETUP_TABLE = """
                CREATE TABLE IF NOT EXISTS tradingdeals (
                    id varchar(255),
                    name varchar(255),
                    cardId varchar(255),
                    minimumDamage double precision,
                    cardType varchar(255),
                    PRIMARY KEY (id),
                    FOREIGN KEY (name) REFERENCES Users (name),
                    FOREIGN KEY (cardId) REFERENCES cards (id)
                );
            """;

    public TradingRepositoryImpl(DbConnector dataSource) {
        this.dataSource = dataSource;
        try (PreparedStatement ps = dataSource.getConnection()
                .prepareStatement(SETUP_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to setup up table", e);
        }
    }

    @Override
    public void createTradingDeal(String username, TradingDeal tradingDeal) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(INSERT_TRADING_DEAL_SQL)) {
                ps.setString(1, tradingDeal.getId());
                ps.setString(2, username);
                ps.setString(3, tradingDeal.getCardToTrade());
                ps.setDouble(4, tradingDeal.getMinimumDamage());
                ps.setString(5, tradingDeal.getType().toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create trading deal", e);
        }
    }

    @Override
    public void deleteTradingDeal(String id) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(DELETE_TRADING_DEAL)) {
                ps.setString(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to delete trading deal", e);
        }
    }

    @Override
    public TradingDeal getTradingDeal(String tradingDealId) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_TRADING_DEAL_SQL)) {
                ps.setString(1, tradingDealId);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return convertResultSetToTradingDeal(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get trading deal", e);
        }
        return null;
    }

    @Override
    public List<TradingDeal> getTradingDeals(String username) {
        List<TradingDeal> tradingDeals = new ArrayList<>();
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_ALL_TRADING_DEALS_FOR_USER_SQL)) {
                ps.setString(1,username);
                ResultSet resultSet = ps.executeQuery();
                while(resultSet.next()) {
                    tradingDeals.add(convertResultSetToTradingDeal(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get trading deals", e);
        }
        return tradingDeals;
    }

    @Override
    public List<String> getTradingDealsIdsFromUser(String username) {
        List<String> tradingDealsIds = new ArrayList<>();
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_ALL_TRADING_DEAL_IDS_FROM_USER_SQL)) {
                ps.setString(1, username);
                ResultSet resultSet = ps.executeQuery();
                while(resultSet.next()) {
                    tradingDealsIds.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get trading deals ids", e);
        }
        return tradingDealsIds;
    }

    @Override
    public String getTradingDealOwner(String tradingDealId) {
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(SELECT_TRADING_DEAL_OWNER_SQL)) {
                ps.setString(1, tradingDealId);
                ResultSet resultSet = ps.executeQuery();
                if(resultSet.next()) {
                    return resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to get trading deals ids", e);
        }
        return null;
    }

    private static TradingDeal convertResultSetToTradingDeal(ResultSet resultSet) throws SQLException {
        return new TradingDeal(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getDouble(4)
        );
    }

}
