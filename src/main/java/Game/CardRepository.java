package Game;

public class CardRepository extends CardCollection {

    public CardRepository() {
        collection.add(new MonsterCard("WaterGoblin", 10, ElementType.water));
        collection.add(new MonsterCard("FireTroll", 15, ElementType.fire));
        collection.add(new MonsterCard("Warrior", 5, ElementType.normal));
        collection.add(new MonsterCard("FirePaladin", 12, ElementType.fire));
        collection.add(new MonsterCard("WaterHunter", 16, ElementType.water));
        collection.add(new MonsterCard("FireMage", 18, ElementType.fire));
        collection.add(new MonsterCard("Dragon", 10, ElementType.normal));
    }
}
