package game.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties({"element", "cardType"})
public class Card {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Damage")
    private Double damage;

    @JsonProperty("Id")
    private String id;

    private ElementType element;

    private CardType cardType;

    public String getName() {
        return name;
    }

    public Double getDamage() {
        return damage;
    }
    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public ElementType getElement() {
        return element;
    }

    public Card(String name, Double damage, ElementType element, String id, CardType cardType) {
        this.name = name;
        this.damage = damage;
        this.element = element;
        this.id = id;
        this.cardType = cardType;
    }

    @JsonCreator
    public Card(@JsonProperty("id") String id, @JsonProperty("Name") String name, @JsonProperty("Damage") Double damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;

        if(name.contains("Spell")) {
            this.cardType = CardType.spell;
        } else {
            this.cardType = CardType.monster;
        }

        if(name.startsWith("Fire")) {
            this.element = ElementType.fire;
        } else if(name.startsWith("Water")) {
            this.element = ElementType.water;
        } else {
            this.element = ElementType.normal;
        }
    }

    public double getCalculatedDamage(Card enemy) {
        // special rules
        if(name.toLowerCase().contains("goblin") && enemy.name.toLowerCase().contains("dragon")) {
            return 0;
        } else if (name.toLowerCase().contains("ork") && enemy.name.toLowerCase().contains("wizard")) {
            return 0;
        } else if (name.toLowerCase().contains("knight") && (enemy.cardType == CardType.spell && enemy.element == ElementType.water)) {
            return 0;
        } else if (cardType == CardType.spell && enemy.name.toLowerCase().contains("kraken")) {
            return 0;
        } else if (name.toLowerCase().contains("dragon") && (enemy.name.toLowerCase().contains("elf") && enemy.element == ElementType.fire)) {
            return 0;
        }

        if(cardType == CardType.monster && enemy.cardType == CardType.monster) {
            return damage;
        }
        else {
            double modifier = element.getModifier(enemy.getElement());
            return damage * modifier;
        }
    }
}
