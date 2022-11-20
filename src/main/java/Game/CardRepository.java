package Game;

public class CardRepository extends CardCollection {

    public CardRepository() {
        addCardToCollection(new MonsterCard("WaterGoblin", 10, ElementType.water));
        addCardToCollection(new MonsterCard("FireTroll", 15, ElementType.fire));
        addCardToCollection(new MonsterCard("Warrior", 5, ElementType.normal));
        addCardToCollection(new MonsterCard("FirePaladin", 12, ElementType.fire));
        addCardToCollection(new MonsterCard("WaterHunter", 16, ElementType.water));
        addCardToCollection(new MonsterCard("FireMage", 18, ElementType.fire));
        addCardToCollection(new MonsterCard("Dragon", 10, ElementType.normal));
    }
}
