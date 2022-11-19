package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardCollection {

    protected List<Card> collection = new ArrayList<>();

    public List<Card> getCollection() {
        return collection;
    }

    public CardCollection getPackageFromCollection() {
        CardCollection Package = new CardCollection();
        for (int i = 0; i < 5; i++) {
            Package.addCardToCollection(getAndRemoveRandomCardFromCollection());
        }
        return Package;
    }

    public void setCollection(List<Card> collection) {
        this.collection = collection;
    }

    public void addCardToCollection(Card newCard) {
        collection.add(newCard);
    }

    public Card getAndRemoveCardFromCollection(int CardIndex)
    {
        return collection.remove(CardIndex);
    }

    public Card getCardFromCollection(int CardIndex)
    {
        return collection.get(CardIndex);
    }

    public Card getAndRemoveRandomCardFromCollection() {
        Random rand = new Random();
        int randCardIndex = rand.nextInt(collection.size());
        return getAndRemoveCardFromCollection(randCardIndex);
    }

    public void printCollection() {
        for (int i = 0; i < collection.size(); i++) {
            System.out.println(i+1 + ": " + collection.get(i).getName());
        }
    }
}
