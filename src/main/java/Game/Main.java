package Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        User PlayerA = new User("PlayerA", 100);
        User PlayerB = new User("PlayerB", 100);

        PlayerA.chooseCards();
        PlayerB.chooseCards();

        Game game = new Game(PlayerA, PlayerB);

        game.play();

    }
}