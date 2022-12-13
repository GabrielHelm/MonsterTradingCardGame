package Game;

import Game.Card.MonsterCard;
import Game.Card.SpellCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FightTest {

    Fight fight;
    Player player1;
    Player player2;
    MonsterCard monster;
    SpellCard spell;

    @BeforeEach
    void init() {
        fight = new Fight();
        player1 = new Player("User1", "1234", 100);
        player2 = new Player("User2", "1234", 100);
        monster = new MonsterCard("Monster", 10.0, ElementType.fire, "2a577907-9ee5-4161-9491-cfc7d05487a7");
        spell = new SpellCard("Spell", 10.0, ElementType.normal, "f3800eff-8961-4663-b1a2-ca67fa990d5a");
    }

    @Test
    void testDrawFight() {
        player1.getDeck().addCardToCollection(monster);
        player2.getDeck().addCardToCollection(monster);

        String logLine = fight.fightOneRound(player1, player2);

        assertEquals("User1: Monster (10.0 Damage) vs. User2: Monster (10.0 Damage) => 10.0 VS 10.0 => Draw", logLine);
        assertEquals(1, player1.getDeck().getCollectionSize());
        assertEquals(1, player2.getDeck().getCollectionSize());
    }

    @Test
    void testUser1WinnerFight() {
        player1.getDeck().addCardToCollection(monster);
        player2.getDeck().addCardToCollection(spell);

        String logLine = fight.fightOneRound(player1, player2);

        assertEquals("User1: Monster (10.0 Damage) vs. User2: Spell (10.0 Damage) => 20.0 VS 5.0 => Monster wins", logLine);
        assertEquals(2, player1.getDeck().getCollectionSize());
        assertEquals(0, player2.getDeck().getCollectionSize());
    }

    @Test
    void testUser2WinnerFight() {

        player1.getDeck().addCardToCollection(spell);
        player2.getDeck().addCardToCollection(monster);

        String logLine = fight.fightOneRound(player1, player2);

        assertEquals("User1: Spell (10.0 Damage) vs. User2: Monster (10.0 Damage) => 5.0 VS 20.0 => Monster wins", logLine);
        assertEquals(0, player1.getDeck().getCollectionSize());
        assertEquals(2, player2.getDeck().getCollectionSize());
    }

}