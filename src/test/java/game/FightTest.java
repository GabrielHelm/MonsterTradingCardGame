package game;

import game.card.Card;
import game.card.CardType;
import game.card.ElementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FightTest {

    private Fight fight;
    private User user1;
    private User user2;
    private Card monster;
    private Card spell;

    @BeforeEach
    void init() {
        fight = new Fight();
        user1 = new User("User1", "1234", 100);
        user2 = new User("User2", "1234", 100);
        monster = new Card("Monster", 10.0, ElementType.fire, "2a577907-9ee5-4161-9491-cfc7d05487a7", CardType.monster);
        spell = new Card("Spell", 10.0, ElementType.normal, "f3800eff-8961-4663-b1a2-ca67fa990d5a", CardType.spell);
    }

    @Test
    void testDrawFight() {
        user1.getDeck().addCardToCollection(monster);
        user2.getDeck().addCardToCollection(monster);

        String logLine = fight.fightOneRound(user1, user2);

        assertEquals("User1: Monster (10.0 Damage) vs. User2: Monster (10.0 Damage) => 10.0 VS 10.0 => Draw", logLine);
        assertEquals(1, user1.getDeck().getCollectionSize());
        assertEquals(1, user2.getDeck().getCollectionSize());
    }

    @Test
    void testUser1WinnerFight() {
        user1.getDeck().addCardToCollection(monster);
        user2.getDeck().addCardToCollection(spell);

        String logLine = fight.fightOneRound(user1, user2);

        assertEquals("User1: Monster (10.0 Damage) vs. User2: Spell (10.0 Damage) => 20.0 VS 5.0 => Monster wins", logLine);
        assertEquals(2, user1.getDeck().getCollectionSize());
        assertEquals(0, user2.getDeck().getCollectionSize());
    }

    @Test
    void testUser2WinnerFight() {

        user1.getDeck().addCardToCollection(spell);
        user2.getDeck().addCardToCollection(monster);

        String logLine = fight.fightOneRound(user1, user2);

        assertEquals("User1: Spell (10.0 Damage) vs. User2: Monster (10.0 Damage) => 5.0 VS 20.0 => Monster wins", logLine);
        assertEquals(0, user1.getDeck().getCollectionSize());
        assertEquals(2, user2.getDeck().getCollectionSize());
    }

}