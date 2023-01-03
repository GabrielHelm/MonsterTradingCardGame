package game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private User user1;
    private User user2;
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

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Game(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        log = new ArrayList<>();
    }

    public void play()
    {
        for (int i = 1; i <= 100; i++) {
            playRound();
            if(user1.getDeck().checkIfCollectionIsEmpty() || user2.getDeck().checkIfCollectionIsEmpty())
            {
                break;
            }
        }
        printLog();
        System.out.println("User 1:");
        user1.getDeck().printCollection();
        System.out.println("User 2:");
        user2.getDeck().printCollection();
    }

    public void playRound()
    {
        Fight fight = new Fight();
        String logLine = fight.fightOneRound(user1, user2);
        log.add(logLine);
    }
}
