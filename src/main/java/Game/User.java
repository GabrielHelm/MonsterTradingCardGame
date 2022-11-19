package Game;

import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private Integer coins;
    private Integer elo;
    private CardCollection Stack;
    private CardCollection Deck;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Integer getElo() {
        return elo;
    }

    public void setElo(Integer elo) {
        this.elo = elo;
    }

    public CardCollection getStack() {
        return Stack;
    }

    public void setStack(CardCollection stack) {
        Stack = stack;
    }

    public CardCollection getDeck() {
        return Deck;
    }

    public void setDeck(CardCollection deck) {
        Deck = deck;
    }
    public User(String username, Integer elo) {
        setCoins(20);
        setUsername(username);
        setElo(elo);
        CardRepository cardRepository = new CardRepository();
        Stack = cardRepository.getPackageFromCollection();
        Deck = new CardCollection();
    }

    public void chooseCards() {
        for (int i = 0; i < 4; i++) {
            int CardIndex;
            Scanner in = new Scanner(System.in);
            do {
                System.out.println("Choose 1 of the following Cards to put it in your Deck:");
                Stack.printCollection();
                while(!in.hasNextInt()) {
                    System.out.println("Please enter a number!");
                    in.next();
                }
                CardIndex = in.nextInt();
            } while(CardIndex < 0 || CardIndex > (5 - i));
            Deck.addCardToCollection(Stack.getAndRemoveCardFromCollection(CardIndex-1));
        }
        System.out.println("Your current Deck:");
        Deck.printCollection();
    }
}
