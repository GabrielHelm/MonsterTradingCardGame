package Game;

import Game.Card.Card;
import Game.Card.CardCollection;

public class Fight {

    String logLine;

    public String fightOneRound(Player player1, Player player2) {

        CardCollection User1Deck = player1.getDeck();
        CardCollection User2Deck = player2.getDeck();

        Card card1 = User1Deck.getAndRemoveRandomCardFromCollection();
        Card card2 = User2Deck.getAndRemoveRandomCardFromCollection();

        double card1CalculatedDamage = card1.getCalculatedDamage(card2);
        double card2CalculatedDamage = card2.getCalculatedDamage(card1);

        SetLogLine(card1, card2, player1, player2);

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

    public void SetLogLine(Card card1, Card card2, Player player1, Player player2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(player1.getUsername());
        stringBuilder.append(": ");
        stringBuilder.append(card1.getName());
        stringBuilder.append(" (");
        stringBuilder.append(card1.getDamage());
        stringBuilder.append(" Damage) vs. ");

        stringBuilder.append(player2.getUsername());
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
