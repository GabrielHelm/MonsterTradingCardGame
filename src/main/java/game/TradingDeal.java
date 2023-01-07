package game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import game.card.CardType;

public class TradingDeal {
    private String id;
    private String cardToTrade;
    private CardType type;
    private double minimumDamage;

    @JsonCreator
    public TradingDeal(@JsonProperty("Id") String id, @JsonProperty("CardToTrade") String cardToTrade, @JsonProperty("Type") String type, @JsonProperty("MinimumDamage") double minimumDamage) {
        this.id = id;
        this.cardToTrade = cardToTrade;
        this.type = CardType.valueOf(type);
        this.minimumDamage = minimumDamage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardToTrade() {
        return cardToTrade;
    }

    public void setCardToTrade(String cardToTrade) {
        this.cardToTrade = cardToTrade;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public double getMinimumDamage() {
        return minimumDamage;
    }

    public void setMinimumDamage(double minimumDamage) {
        this.minimumDamage = minimumDamage;
    }
}
