package game.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MonsterCard extends Card {
    public MonsterCard(String name, Double damage, ElementType element, String id) {
        super(name, damage, element, id);
    }

    public MonsterCard(SpellCard spellCard) {
        super(spellCard.name, spellCard.damage, spellCard.element, spellCard.id);
    }

    public MonsterCard()
    {
        super();
    }
    @JsonCreator
    public MonsterCard(@JsonProperty("id") String id, @JsonProperty("Name") String name, @JsonProperty("Damage") Double damage) {
        super(name, damage, ElementType.fire, id);
    }

    public double getCalculatedDamage(Card enemy)
    {
        if(enemy.getClass() == MonsterCard.class) {
            return damage;
        }
        else {
            double modifier = element.getModifier(enemy.getElement());
            return damage * modifier;
        }
    }
}
