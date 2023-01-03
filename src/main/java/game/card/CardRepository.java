package game.card;

public class CardRepository extends CardCollection {

    public CardRepository() {
        addCardToCollection(new Card("WaterGoblin", 10.0, ElementType.water, "1", CardType.monster));
        addCardToCollection(new Card("FireTroll", 15.0, ElementType.fire, "2", CardType.monster));
        addCardToCollection(new Card("Warrior", 5.0, ElementType.normal, "3", CardType.monster));
        addCardToCollection(new Card("FirePaladin", 12.0, ElementType.fire, "4", CardType.monster));
        addCardToCollection(new Card("WaterHunter", 16.0, ElementType.water, "5", CardType.monster));
        addCardToCollection(new Card("FireMage", 18.0, ElementType.fire, "6", CardType.monster));
        addCardToCollection(new Card("Dragon", 10.0, ElementType.normal, "7", CardType.monster));
    }
}
