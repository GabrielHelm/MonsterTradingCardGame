package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpellCardTest {
    SpellCard spellCard;
    SpellCard enemySpellCard;
    MonsterCard enemyMonsterCard;

    @BeforeEach
    void init() {
        spellCard = new SpellCard("FireSpell", 10, ElementType.fire);
        enemySpellCard = new SpellCard("Waterspell", 10, ElementType.normal);
        enemyMonsterCard = new MonsterCard("Goblin", 10, ElementType.normal);
    }

    @Test
    void testCalculatedDamageWithTwoSpellCards() {
        double calculatedDamage = spellCard.getCalculatedDamage(enemySpellCard);

        assertEquals(20, calculatedDamage);
    }

    @Test
    void testCalculatedDamageWithSpellCardAndMonsterCard() {
        double calculatedDamage = spellCard.getCalculatedDamage(enemyMonsterCard);

        assertEquals(20, calculatedDamage);
    }
}