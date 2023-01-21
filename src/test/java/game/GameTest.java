package game;

import game.ParsingClasses.UserStats;
import game.card.Card;
import game.card.CardType;
import game.card.ElementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private User user1;
    private User user2;
    private Card monster;
    private Card spell;
    private Game game;
    private UserStats userStatsUser1;
    private UserStats userStatsUser2;

    @BeforeEach
    void init() {
        game = new Game();
        user1 = new User("User1", "1234", 100);
        user2 = new User("User2", "1234", 100);
        monster = new Card("Monster", 10.0, ElementType.fire, "2a577907-9ee5-4161-9491-cfc7d05487a7", CardType.monster);
        spell = new Card("Spell", 10.0, ElementType.normal, "f3800eff-8961-4663-b1a2-ca67fa990d5a", CardType.spell);
        userStatsUser1 = new UserStats("User", 100, 0, 0);
        userStatsUser2 = new UserStats("User", 100, 0, 0);
    }

    @Test
    void testWinnerGame() {
        user1.getDeck().addCardToCollection(monster);
        user2.getDeck().addCardToCollection(spell);
        user1.setUserStats(userStatsUser1);
        user2.setUserStats(userStatsUser2);
        game.setUser1(user1);
        game.setUser2(user2);


        game.play();

        assertEquals("Round 1: User1: Monster (10.0 Damage) vs. User2: Spell (10.0 Damage) => 20.0 VS 5.0 => Monster wins\n", game.getLogAsString());
        assertEquals(2, user1.getDeck().getCollectionSize());
        assertEquals(0, user2.getDeck().getCollectionSize());
        assertEquals(103, user1.getUserStats().getElo());
        assertEquals(1, user1.getUserStats().getWins());
        assertEquals(0, user1.getUserStats().getLosses());
        assertEquals(95, user2.getUserStats().getElo());
        assertEquals(0, user2.getUserStats().getWins());
        assertEquals(1, user2.getUserStats().getLosses());
    }

    @Test
    void testDrawGame() {
        user1.getDeck().addCardToCollection(monster);
        user2.getDeck().addCardToCollection(monster);
        user1.setUserStats(userStatsUser1);
        user2.setUserStats(userStatsUser2);
        game.setUser1(user1);
        game.setUser2(user2);


        game.play();

        assertEquals(1, user1.getDeck().getCollectionSize());
        assertEquals(1, user2.getDeck().getCollectionSize());
        assertEquals(100, user1.getUserStats().getElo());
        assertEquals(0, user1.getUserStats().getWins());
        assertEquals(0, user1.getUserStats().getLosses());
        assertEquals(100, user2.getUserStats().getElo());
        assertEquals(0, user2.getUserStats().getWins());
        assertEquals(0, user2.getUserStats().getLosses());
    }

}