package game.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {
    Card monsterCard;
    Card spellCard;
    Card enemyMonsterCard;
    Card enemySpellCard;

    @BeforeEach
    void init() {
        monsterCard = new Card("Dragon", 10.0, ElementType.fire, "fd5fb3ca-3ee2-4314-87ba-17c4545b2253", CardType.monster);
        spellCard = new Card("Spell", 10.0, ElementType.fire, "fd5fb3ca-3ee2-4314-87ba-17c4545b2253", CardType.spell);
        enemyMonsterCard = new Card("Org", 10.0, ElementType.normal, "263198b3-b9fb-46de-9a38-d4fc686aa2de", CardType.monster);
        enemySpellCard = new Card("Spell1", 10.0, ElementType.normal, "9e21a940-6812-48ec-abc7-2029095ec637", CardType.spell);
    }

    @Test
    void testCalculatedDamageWithTwoMonsterCards() {
        double calculatedDamage = monsterCard.getCalculatedDamage(enemyMonsterCard);

        assertEquals(10.0, calculatedDamage);
    }

    @Test
    void testCalculatedDamageWithSpellCardAndMonsterCard() {
        double calculatedDamage = monsterCard.getCalculatedDamage(enemySpellCard);

        assertEquals(20.0, calculatedDamage);
    }

    @Test
    void testCalculatedDamageWithTwoSpellCards() {
        double calculatedDamage = spellCard.getCalculatedDamage(enemySpellCard);

        assertEquals(20.0, calculatedDamage);
    }
}