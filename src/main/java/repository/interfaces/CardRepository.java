package repository.interfaces;

import game.card.Card;

public interface CardRepository {
    void createCard(Card card);
    Card getCard(String id);
}
