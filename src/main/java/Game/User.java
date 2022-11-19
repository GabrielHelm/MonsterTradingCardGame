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

    public User(String username, String password, Integer elo) {
        setCoins(20);
        setUsername(username);
        setPassword(password);
        setElo(elo);
        CardRepository cardRepository = new CardRepository();
        Stack = cardRepository.getPackageFromCollection();
        Deck = new CardCollection();
    }
    public User() {
        setCoins(20);
        setUsername("DefaultUser");
        setPassword("1234");
        setElo(100);
        CardRepository cardRepository = new CardRepository();
        Stack = cardRepository.getPackageFromCollection();
        Deck = new CardCollection();
    }

    public void chooseCards() {
        for (int i = 0; i < 4; i++) {
            int cardindex = UserInputChooseCardIndex();
            Deck.addCardToCollection(Stack.getAndRemoveCardFromCollection(cardindex-1));
        }
        System.out.println("Your current Deck:");
        Deck.printCollection();
    }

    public int UserInputChooseCardIndex()
    {
        int cardIndex;
        Scanner in = new Scanner(System.in);
        System.out.println("Choose 1 of the following Cards to put it in your Deck:");
        Stack.printCollection();
        while(!in.hasNextInt()) {
            System.out.println("Please enter a number!");
            if(in.hasNext()) {
                in.next();
            }
        }
        cardIndex = in.nextInt();
        return cardIndex;
    }
}