package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MonsterCardTest {
    MonsterCard monsterCard;
    MonsterCard enemyMonsterCard;
    SpellCard enemySpellCard;

    @BeforeEach
    void init() {
        monsterCard = new MonsterCard("Dragon", 10, ElementType.fire);
        enemyMonsterCard = new MonsterCard("Org", 10, ElementType.normal);
        enemySpellCard = new SpellCard("Spell1", 10, ElementType.normal);
    }

    @Test
    void testCalculatedDamageWithTwoMonsterCards() {
        double calculatedDamage = monsterCard.getCalculatedDamage(enemyMonsterCard);

        assertEquals(10, calculatedDamage);
    }

    @Test
    void testCalculatedDamageWithSpellCardAndMonsterCard() {
        double calculatedDamage = monsterCard.getCalculatedDamage(enemySpellCard);

        assertEquals(20, calculatedDamage);
    }
}