package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user1;

    @BeforeEach
    void init() {
        user1 = new User();
    }

    @Test
    void testDefaultUserInit() {
        assertEquals("DefaultUser", user1.getCredentials().getUsername());
        assertEquals("1234", user1.getCredentials().getPassword());
        assertEquals(20, user1.getCoins());
    }

    @Test
    void testCustomUserInit() {
        user1 = new User("TestUser", "Password", 500);
        assertEquals("TestUser", user1.getCredentials().getUsername());
        assertEquals("Password", user1.getCredentials().getPassword());
        assertEquals(20, user1.getCoins());
    }

    @Test
    void testStackSize() {
        assertEquals(5, user1.getStack().getCollectionSize());
    }

    @Test
    void testCorrectUserInput() {
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Scanner sc = new Scanner(System.in);

        int Userinput = user1.UserInputChooseCardIndex(sc);

        assertEquals(1, Userinput);
    }

    @Test
    void testFalseIntoCorrectUserInput() {
        String input = "g" + System.getProperty("line.separator") + "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);

        int Userinput = user1.UserInputChooseCardIndex(sc);

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

        user1.chooseCards();

        assertEquals(4, user1.getDeck().getCollectionSize());
        assertEquals(1, user1.getStack().getCollectionSize());
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

        user1.chooseCards();

        assertEquals(4, user1.getDeck().getCollectionSize());
        assertEquals(1, user1.getStack().getCollectionSize());
    }

    @Test
    void testIfStackIsEmpty() {

        for (int i = 0; i < 5; i++) {
            user1.getStack().getAndRemoveRandomCardFromCollection();
        }
        assertTrue(user1.getStack().checkIfCollectionIsEmpty());
    }
}