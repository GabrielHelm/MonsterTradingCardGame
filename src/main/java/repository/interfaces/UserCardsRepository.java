package repository.interfaces;

import java.util.List;

public interface UserCardsRepository {
    void addCardToUserCards(String cardId, String username);

    List<String> getAllCardIdsFromUserCards(String username);

    List<String> getAllCardIdsFromUserDeck(String username);
    void updateCardChangeToDeck(String cardId, String username);
}
