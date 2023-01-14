package game.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardCollection {

    private String u_ID;
    private List<Card> cards = new ArrayList<>();

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCardToCollection(Card newCard) {
        cards.add(newCard);
    }

    public Card getAndRemoveCardFromCollection(int CardIndex)
    {
        return cards.remove(CardIndex);
    }

    public Card getCardFromCollection(int CardIndex)
    {
        return cards.get(CardIndex);
    }

    public int getCollectionSize()
    {
        return cards.size();
    }

    public boolean checkIfCollectionIsEmpty()
    {
        return cards.isEmpty();
    }

    public Card getAndRemoveRandomCardFromCollection() {
        Random rand = new Random();
        int randCardIndex = rand.nextInt(cards.size());
        return getAndRemoveCardFromCollection(randCardIndex);
    }

    public void printCollection() {
        for (int i = 0; i < cards.size(); i++) {
            System.out.println(i+1 + ": " + cards.get(i).getName() + " Element: " + cards.get(i).getElement() + " Damage: " + cards.get(i).getDamage() + " Id: " + cards.get(i).getId() + " CardType: " + cards.get(i).getCardType());
        }
    }

    public String getU_ID() {
        return u_ID;
    }

    public void setU_ID(String u_ID) {
        this.u_ID = u_ID;
    }
}
