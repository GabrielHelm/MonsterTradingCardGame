package Game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Player player1;
    private Player player2;
    private List<String> log;

    public List<String> getLog() {
        return log;
    }

    public void setLog(List<String> log) {
        this.log = log;
    }

    public void printLog() {
        log.forEach(System.out::println);
    }

    public Player getUser1() {
        return player1;
    }

    public void setUser1(Player player1) {
        this.player1 = player1;
    }

    public Player getUser2() {
        return player2;
    }

    public void setUser2(Player player2) {
        this.player2 = player2;
    }

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        log = new ArrayList<>();
    }

    public void play()
    {
        for (int i = 1; i <= 100; i++) {
            playRound();
            if(player1.getDeck().checkIfCollectionIsEmpty() || player2.getDeck().checkIfCollectionIsEmpty())
            {
                break;
            }
        }
        printLog();
        System.out.println("User 1:");
        player1.getDeck().printCollection();
        System.out.println("User 2:");
        player2.getDeck().printCollection();
    }

    public void playRound()
    {
        Fight fight = new Fight();
        String logLine = fight.fightOneRound(player1, player2);
        log.add(logLine);
    }
}
