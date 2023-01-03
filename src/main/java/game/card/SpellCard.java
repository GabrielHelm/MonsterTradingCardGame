package game.card;

public class SpellCard extends Card {
    public SpellCard(String name, Double damage, ElementType element, String id) {
        super(name, damage, element, id);
    }

    public SpellCard(MonsterCard monsterCard) {
        super(monsterCard.name, monsterCard.damage, monsterCard.element, monsterCard.id);
    }

    public SpellCard()
    {
        super();
    }

    public double getCalculatedDamage(Card enemy)
    {
        double modifier = element.getModifier(enemy.getElement());
        return damage * modifier;
    }
}
