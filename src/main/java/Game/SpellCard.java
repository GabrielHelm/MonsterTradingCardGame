package Game;

public class SpellCard extends Card{
    public SpellCard(String name, Integer damage, ElementType element) {
        super(name, damage, element);
    }

    public double getCalculatedDamage(Card enemy)
    {
        double Modifier = element.getModifier(enemy.element);
        return damage * Modifier;
    }
}
