package game.card;

public class CardRepository extends CardCollection {

    public CardRepository() {
        addCardToCollection(new MonsterCard("WaterGoblin", 10.0, ElementType.water, "1"));
        addCardToCollection(new MonsterCard("FireTroll", 15.0, ElementType.fire, "2"));
        addCardToCollection(new MonsterCard("Warrior", 5.0, ElementType.normal, "3"));
        addCardToCollection(new MonsterCard("FirePaladin", 12.0, ElementType.fire, "4"));
        addCardToCollection(new MonsterCard("WaterHunter", 16.0, ElementType.water, "5"));
        addCardToCollection(new MonsterCard("FireMage", 18.0, ElementType.fire, "6"));
        addCardToCollection(new MonsterCard("Dragon", 10.0, ElementType.normal, "7"));
    }
}
