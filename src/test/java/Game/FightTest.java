package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FightTest {

    Fight fight;
    User user1;
    User user2;
    MonsterCard monster;
    SpellCard spell;

    @BeforeEach
    void init() {
        fight = new Fight();
        user1 = new User("User1", "1234", 100);
        user2 = new User("User2", "1234", 100);
        monster = new MonsterCard("Monster", 10, ElementType.fire);
        spell = new SpellCard("Spell", 10, ElementType.normal);
    }

    @Test
    void testDrawFight() {
        user1.getDeck().addCardToCollection(monster);
        user2.getDeck().addCardToCollection(monster);

        String logLine = fight.fightOneRound(user1, user2);

        assertEquals("User1: Monster (10 Damage) vs. User2: Monster (10 Damage) => 10.0 VS 10.0 => Draw", logLine);
        assertEquals(1, user1.getDeck().getCollectionSize());
        assertEquals(1, user2.getDeck().getCollectionSize());
    }

    @Test
    void testUser1WinnerFight() {
        user1.getDeck().addCardToCollection(monster);
        user2.getDeck().addCardToCollection(spell);

        String logLine = fight.fightOneRound(user1, user2);

        assertEquals("User1: Monster (10 Damage) vs. User2: Spell (10 Damage) => 20.0 VS 5.0 => Monster wins", logLine);
        assertEquals(2, user1.getDeck().getCollectionSize());
        assertEquals(0, user2.getDeck().getCollectionSize());
    }

    @Test
    void testUser2WinnerFight() {

        user1.getDeck().addCardToCollection(spell);
        user2.getDeck().addCardToCollection(monster);

        String logLine = fight.fightOneRound(user1, user2);

        assertEquals("User1: Spell (10 Damage) vs. User2: Monster (10 Damage) => 5.0 VS 20.0 => Monster wins", logLine);
        assertEquals(0, user1.getDeck().getCollectionSize());
        assertEquals(2, user2.getDeck().getCollectionSize());
    }

}