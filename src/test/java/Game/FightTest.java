package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FightTest {

    Fight fight;
    private static final User user1 = mock(User.class);
    private static final User user2 = mock(User.class);

    private static final CardCollection collectionUser1 = mock(CardCollection.class);
    private static final CardCollection collectionUser2 = mock(CardCollection.class);

    private static final MonsterCard CardUser1 = mock(MonsterCard.class);

    private static final MonsterCard CardUser2 = mock(MonsterCard.class);

    @BeforeEach
    void init() {
        fight = new Fight();
    }

    @Test
    void testDrawFight() {
        when(user1.getDeck()).thenReturn(collectionUser1);
        when(user2.getDeck()).thenReturn(collectionUser2);
        when(user1.getUsername()).thenReturn("User1");
        when(user2.getUsername()).thenReturn("User2");
        when(collectionUser1.getAndRemoveRandomCardFromCollection()).thenReturn(CardUser1);
        when(collectionUser2.getAndRemoveRandomCardFromCollection()).thenReturn(CardUser2);
        when(CardUser1.getName()).thenReturn("Monster1");
        when(CardUser2.getName()).thenReturn("Monster2");
        when(CardUser1.getDamage()).thenReturn(10);
        when(CardUser2.getDamage()).thenReturn(10);

        when(CardUser1.getCalculatedDamage(CardUser2)).thenReturn(10.0);
        when(CardUser2.getCalculatedDamage(CardUser1)).thenReturn(10.0);

        String logLine = fight.fightOneRound(user1, user2);

        assertEquals("User1: Monster1 (10 Damage) vs. User2: Monster2 (10 Damage) => 10.0 VS 10.0 => Draw", logLine);
    }

    @Test
    void testUser1WinnerFight() {
        when(user1.getDeck()).thenReturn(collectionUser1);
        when(user2.getDeck()).thenReturn(collectionUser2);
        when(user1.getUsername()).thenReturn("User1");
        when(user2.getUsername()).thenReturn("User2");
        when(collectionUser1.getAndRemoveRandomCardFromCollection()).thenReturn(CardUser1);
        when(collectionUser2.getAndRemoveRandomCardFromCollection()).thenReturn(CardUser2);
        when(CardUser1.getName()).thenReturn("Monster1");
        when(CardUser2.getName()).thenReturn("Spell2");
        when(CardUser1.getDamage()).thenReturn(10);
        when(CardUser2.getDamage()).thenReturn(10);

        when(CardUser1.getCalculatedDamage(CardUser2)).thenReturn(20.0);
        when(CardUser2.getCalculatedDamage(CardUser1)).thenReturn(5.0);

        String logLine = fight.fightOneRound(user1, user2);

        assertEquals("User1: Monster1 (10 Damage) vs. User2: Spell2 (10 Damage) => 20.0 VS 5.0 => Monster1 wins", logLine);
    }

    @Test
    void testUser2WinnerFight() {
        when(user1.getDeck()).thenReturn(collectionUser1);
        when(user2.getDeck()).thenReturn(collectionUser2);
        when(user1.getUsername()).thenReturn("User1");
        when(user2.getUsername()).thenReturn("User2");
        when(collectionUser1.getAndRemoveRandomCardFromCollection()).thenReturn(CardUser1);
        when(collectionUser2.getAndRemoveRandomCardFromCollection()).thenReturn(CardUser2);
        when(CardUser1.getName()).thenReturn("Monster1");
        when(CardUser2.getName()).thenReturn("Spell2");
        when(CardUser1.getDamage()).thenReturn(10);
        when(CardUser2.getDamage()).thenReturn(10);

        when(CardUser1.getCalculatedDamage(CardUser2)).thenReturn(5.0);
        when(CardUser2.getCalculatedDamage(CardUser1)).thenReturn(20.0);

        String logLine = fight.fightOneRound(user1, user2);

        assertEquals("User1: Monster1 (10 Damage) vs. User2: Spell2 (10 Damage) => 5.0 VS 20.0 => Spell2 wins", logLine);
    }

}