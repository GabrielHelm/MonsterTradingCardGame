package game;

import game.card.CardCollection;
import game.card.CardRepository;

import java.util.Scanner;

public class User extends Credentials{


    private Token token;
    private Integer coins;
    private Integer elo;
    private CardCollection stack;
    private CardCollection deck;

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
        return stack;
    }

    public void setStack(CardCollection stack) {
        this.stack = stack;
    }


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public CardCollection getDeck() {
        return deck;
    }

    public void setDeck(CardCollection deck) {
        this.deck = deck;
    }

    public User(String username, String password, Integer elo) {
        setUsername(username);
        setPassword(password);
        setCoins(20);
        setElo(elo);
        CardRepository cardRepository = new CardRepository();
        stack = cardRepository.getPackageFromCollection();
        deck = new CardCollection();
    }
    public User() {
        setCoins(20);
        setUsername("DefaultUser");
        setPassword("1234");
        setElo(100);
        CardRepository cardRepository = new CardRepository();
        stack = cardRepository.getPackageFromCollection();
        deck = new CardCollection();
    }

    public void chooseCards() {

        Scanner in = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            int cardindex = UserInputChooseCardIndex(in);
            deck.addCardToCollection(stack.getAndRemoveCardFromCollection(cardindex-1));
        }
        System.out.println("Your current Deck:");
        deck.printCollection();
    }

    public int UserInputChooseCardIndex(Scanner in)
    {
        int cardIndex;
        do {
            System.out.println("Choose 1 of the following Cards to put it in your Deck:");
            stack.printCollection();
            while(!in.hasNextInt()) {
                System.out.println("Please enter a number!");
                if(in.hasNext()) {
                    in.next();
                }
            }
            cardIndex = in.nextInt();
        } while(cardIndex <= 0 || cardIndex > stack.getCollectionSize());

        return cardIndex;
    }

    public void generateToken() {
        token = new Token(this);
    }
}