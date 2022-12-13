package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player1;

    @BeforeEach
    void init() {
        player1 = new Player();
    }

    @Test
    void testDefaultUserInit() {
        assertEquals("DefaultUser", player1.getUsername());
        assertEquals("1234", player1.getPassword());
        assertEquals(100, player1.getElo());
        assertEquals(20, player1.getCoins());
    }

    @Test
    void testCustomUserInit() {
        player1 = new Player("TestUser", "Password", 500);
        assertEquals("TestUser", player1.getUsername());
        assertEquals("Password", player1.getPassword());
        assertEquals(500, player1.getElo());
        assertEquals(20, player1.getCoins());
    }

    @Test
    void testStackSize() {
        assertEquals(5, player1.getStack().getCollectionSize());
    }

    @Test
    void testCorrectUserInput() {
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Scanner sc = new Scanner(System.in);

        int Userinput = player1.UserInputChooseCardIndex(sc);

        assertEquals(1, Userinput);
    }

    @Test
    void testFalseIntoCorrectUserInput() {
        String input = "g" + System.getProperty("line.separator") + "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);

        int Userinput = player1.UserInputChooseCardIndex(sc);

        assertEquals(1, Userinput);
    }

    @Test
    void testStackAndDeckAfterTheUserChoseCards() {
        String input = "";
        for(int i = 0; i < 4; i++) {
            input += ("1" + System.getProperty("line.separator"));
        }

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        player1.chooseCards();

        assertEquals(4, player1.getDeck().getCollectionSize());
        assertEquals(1, player1.getStack().getCollectionSize());
    }

    @Test
    void testStackAndDeckAfterTheUserChoseCardsWithAnInvalidInput() {
        String input = "-1" + System.getProperty("line.separator");
        for(int i = 0; i < 3; i++) {
            input += ("1" + System.getProperty("line.separator"));
        }
        input += "5" + System.getProperty("line.separator") + "1";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        player1.chooseCards();

        assertEquals(4, player1.getDeck().getCollectionSize());
        assertEquals(1, player1.getStack().getCollectionSize());
    }

    @Test
    void testIfStackIsEmpty() {

        for (int i = 0; i < 5; i++) {
            player1.getStack().getAndRemoveRandomCardFromCollection();
        }
        assertTrue(player1.getStack().checkIfCollectionIsEmpty());
    }
}