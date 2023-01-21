package game.ParsingClasses;

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

    public String getCardToTrade() {
        return cardToTrade;
    }

    public CardType getType() {
        return type;
    }

    public double getMinimumDamage() {
        return minimumDamage;
    }

}
