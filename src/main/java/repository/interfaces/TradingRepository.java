package repository.interfaces;

import game.ParsingClasses.TradingDeal;

import java.util.List;

public interface TradingRepository {
    void createTradingDeal(String username, TradingDeal tradingDeal);

    void deleteTradingDeal(String id);

    TradingDeal getTradingDeal(String tradingDealId);

    List<TradingDeal> getTradingDeals(String username);

    List<String> getTradingDealsIdsFromUser(String username);

    String getTradingDealOwner(String tradingDealId);
}
