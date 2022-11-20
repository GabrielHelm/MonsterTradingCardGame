package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpellCardTest {
    SpellCard spellCard;
    SpellCard enemySpellCard;
    private static final MonsterCard enemyMonsterCard = mock(MonsterCard.class);

    @BeforeEach
    void init() {
        spellCard = new SpellCard("FireSpell", 10, ElementType.fire);
        enemySpellCard = new SpellCard("Waterspell", 10, ElementType.water);
    }

    @Test
    void testCalculatedDamageWithTwoSpellCards() {
        double calculatedDamage = spellCard.getCalculatedDamage(enemySpellCard);

        assertEquals(5, calculatedDamage);
    }

    @Test
    void testCalculatedDamageWithSpellCardAndMonsterCard() {
        when(enemyMonsterCard.getElement()).thenReturn(ElementType.normal);
        double calculatedDamage = spellCard.getCalculatedDamage(enemyMonsterCard);

        assertEquals(20, calculatedDamage);
    }
}