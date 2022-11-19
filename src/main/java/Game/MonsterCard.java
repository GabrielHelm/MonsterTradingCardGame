package Game;

public class MonsterCard extends Card{
    public MonsterCard(String name, Integer damage, ElementType element) {
        super(name, damage, element);
    }

    public double getCalculatedDamage(Card enemy)
    {
        if(enemy.getClass() == MonsterCard.class) {
            return damage;
        }
        else {
            double Modifier = element.getModifier(enemy.element);
            return damage * Modifier;
        }
    }
}
