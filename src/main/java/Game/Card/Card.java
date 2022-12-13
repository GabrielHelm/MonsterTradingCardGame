package Game.Card;

import Game.ElementType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties({"element"})
public abstract class Card {
    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Damage")
    protected Double damage;
    @JsonProperty("Id")
    protected String id;

    protected ElementType element;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDamage() {
        return damage;
    }

    public ElementType getElement() {
        return element;
    }
    public Card() {
    }

    public void setElement(ElementType element) {
        this.element = element;
    }

    public Card(String name, Double damage, ElementType element, String id) {
        setName(name);
        this.damage = damage;
        setElement(element);
        this.id = id;
    }


    public abstract double getCalculatedDamage(Card enemy);

}
