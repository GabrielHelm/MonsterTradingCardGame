package Game;

import Game.Card.MonsterCard;
import Game.Card.SpellCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MonsterCardTest {
    MonsterCard monsterCard;
    MonsterCard enemyMonsterCard;
    SpellCard enemySpellCard;

    @BeforeEach
    void init() {
        monsterCard = new MonsterCard("Dragon", 10.0, ElementType.fire, "fd5fb3ca-3ee2-4314-87ba-17c4545b2253");
        enemyMonsterCard = new MonsterCard("Org", 10.0, ElementType.normal, "263198b3-b9fb-46de-9a38-d4fc686aa2de");
        enemySpellCard = new SpellCard("Spell1", 10.0, ElementType.normal, "9e21a940-6812-48ec-abc7-2029095ec637");
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
}