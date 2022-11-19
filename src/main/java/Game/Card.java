package Game;

public abstract class Card {
    protected String name;
    protected final Integer damage;
    protected ElementType element;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDamage() {
        return damage;
    }

    public ElementType getElement() {
        return element;
    }

    public void setElement(ElementType element) {
        this.element = element;
    }

    public Card(String name, Integer damage, ElementType element) {
        setName(name);
        this.damage = damage;
        setElement(element);
    }
    public abstract double getCalculatedDamage(Card enemy);

}
