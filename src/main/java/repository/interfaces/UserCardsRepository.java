package repository.interfaces;

import java.util.List;

public interface UserCardsRepository {
    void addCardToUserCards(String cardId, String username);

    void updateCardChangeToStack(String cardId, String username);

    void updateCardChangeToNotAvailable(String cardId, String username);

    void updateCardChangeToAvailable(String cardId, String username);

    void updateCardChangeUser(String cardId, String username);

    boolean checkIfCardIsInUserStackAndAvailable(String cardId, String username);

    List<String> getAllCardIdsFromAvailableUserCards(String username);

    List<String> getAllCardIdsFromAvailableUserStack(String username);

    List<String> getAllCardIdsFromUserCards(String username);

    List<String> getAllCardIdsFromUserDeck(String username);
    void updateCardChangeToDeck(String cardId, String username);
}
