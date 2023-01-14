package game;

import game.card.Card;
import game.card.CardCollection;

import java.util.Random;

public class Fight {

    String logLine;

    public String fightOneRound(User user1, User user2) {

        CardCollection User1Deck = user1.getDeck();
        CardCollection User2Deck = user2.getDeck();

        Card card1 = User1Deck.getAndRemoveRandomCardFromCollection();
        Card card2 = User2Deck.getAndRemoveRandomCardFromCollection();

        double card1CalculatedDamage = card1.getCalculatedDamage(card2);
        double card2CalculatedDamage = card2.getCalculatedDamage(card1);

        SetLogLine(card1, card2, user1, user2);


        if(card1CalculatedDamage > card2CalculatedDamage) {
            User1Deck.addCardToCollection(card1);
            User1Deck.addCardToCollection(card2);
            addWinnerToLogLine(card1CalculatedDamage, card2CalculatedDamage, card1);
        } else if (card1CalculatedDamage < card2CalculatedDamage) {
            User2Deck.addCardToCollection(card1);
            User2Deck.addCardToCollection(card2);
            addWinnerToLogLine(card1CalculatedDamage, card2CalculatedDamage, card2);
        } else {
            User1Deck.addCardToCollection(card1);
            User2Deck.addCardToCollection(card2);
            addDrawToLogLine(card1CalculatedDamage, card2CalculatedDamage);
        }

        return logLine;
    }

    public void SetLogLine(Card card1, Card card2, User user1, User user2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(user1.getCredentials().getUsername());
        stringBuilder.append(": ");
        stringBuilder.append(card1.getName());
        stringBuilder.append(" (");
        stringBuilder.append(card1.getDamage());
        stringBuilder.append(" Damage) vs. ");

        stringBuilder.append(user2.getCredentials().getUsername());
        stringBuilder.append(": ");
        stringBuilder.append(card2.getName());
        stringBuilder.append(" (");
        stringBuilder.append(card2.getDamage());
        stringBuilder.append(" Damage)");

        logLine = stringBuilder.toString();
    }

    public void addDrawToLogLine(double card1CalculatedDamage, double card2CalculatedDamage) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" => ");
        stringBuilder.append(card1CalculatedDamage);
        stringBuilder.append(" VS ");
        stringBuilder.append(card2CalculatedDamage);
        stringBuilder.append(" => Draw");
        logLine += stringBuilder.toString();
    }

    public void addWinnerToLogLine(double card1CalculatedDamage, double card2CalculatedDamage, Card winnerCard) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" => ");
        stringBuilder.append(card1CalculatedDamage);
        stringBuilder.append(" VS ");
        stringBuilder.append(card2CalculatedDamage);
        stringBuilder.append(" => ");
        stringBuilder.append(winnerCard.getName());
        stringBuilder.append(" wins");
        logLine += stringBuilder.toString();
    }
}
