package Game;

import Game.Card.MonsterCard;
import Game.Card.SpellCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SpellCardTest {
    SpellCard spellCard;
    SpellCard enemySpellCard;
    MonsterCard enemyMonsterCard;

    @BeforeEach
    void init() {
        spellCard = new SpellCard("FireSpell", 10.0, ElementType.fire, "7590796e-0c56-4a1d-856a-76eeb7b37695");
        enemySpellCard = new SpellCard("Waterspell", 10.0, ElementType.normal, "a3745f47-4d71-4c9d-9976-953e7c98412b");
        enemyMonsterCard = new MonsterCard("Goblin", 10.0, ElementType.normal, "652616cf-fdfc-4eaa-b259-e9d618b15048");
    }

    @Test
    void testCalculatedDamageWithTwoSpellCards() {
        double calculatedDamage = spellCard.getCalculatedDamage(enemySpellCard);

        assertEquals(20.0, calculatedDamage);
    }

    @Test
    void testCalculatedDamageWithSpellCardAndMonsterCard() {
        double calculatedDamage = spellCard.getCalculatedDamage(enemyMonsterCard);

        assertEquals(20.0, calculatedDamage);
    }
}