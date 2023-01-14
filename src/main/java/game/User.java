package game;

import game.ParsingClasses.Credentials;
import game.ParsingClasses.UserStats;
import game.card.Card;
import game.card.CardCollection;

public class User{
    private Credentials credentials;
    private Token token;
    private Integer coins;
    private CardCollection deck;

    private UserStats userStats;

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public UserStats getUserStats() {
        return userStats;
    }

    public void setUserStats(UserStats userStats) {
        this.userStats = userStats;
    }

    public CardCollection getDeck() {
        return deck;
    }

    public void setDeck(CardCollection deck) {
        this.deck = deck;
    }

    public User(String username, String password, Integer coins) {
        credentials = new Credentials();
        credentials.setUsername(username);
        credentials.setPassword(password);
        setCoins(coins);
        deck = new CardCollection();
    }

    public void generateToken() {
        token = new Token(this);
    }
}