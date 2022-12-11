package Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        User playerA = new User("PlayerA", "wr4gab", 100);
        User playerB = new User("PlayerB", "m(6%2?2m", 100);

        playerA.chooseCards();
        playerB.chooseCards();

        Game game = new Game(playerA, playerB);

        game.play();
    }
}