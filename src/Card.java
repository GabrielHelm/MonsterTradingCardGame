public class Card {
    private String name;
    private final Integer damage;
    private ElementType element;

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

}
