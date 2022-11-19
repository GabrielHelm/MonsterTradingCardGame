package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user1;

    @BeforeEach
    void init() {
        user1 = new User();
    }

    @Test
    void testDefaultUserInit() {
        assertEquals("DefaultUser", user1.getUsername());
        assertEquals("1234", user1.getPassword());
        assertEquals(100, user1.getElo());
        assertEquals(20, user1.getCoins());
    }

    @Test
    void testCustomUserInit() {
        user1 = new User("TestUser", "Password", 500);
        assertEquals("TestUser", user1.getUsername());
        assertEquals("Password", user1.getPassword());
        assertEquals(500, user1.getElo());
        assertEquals(20, user1.getCoins());
    }

    @Test
    void testStackSize() {
        assertEquals(5, user1.getStack().collection.size());
    }

    @Test
    void testCorrectUserInput() {
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        int Userinput = user1.UserInputChooseCardIndex();

        assertEquals(1, Userinput);
    }

    @Test
    void testFalseUserInput() {
        String input = "g";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String input2 = "1";
        InputStream in2= new ByteArrayInputStream(input2.getBytes());
        System.setIn(in2);

        int Userinput = user1.UserInputChooseCardIndex();

        assertEquals(1, Userinput);
    }
}