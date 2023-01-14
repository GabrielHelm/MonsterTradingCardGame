package game;

import game.ParsingClasses.UserProfile;

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

    public Game() {
        this.user1 = null;
        this.user2 = null;
        log = new ArrayList<>();
    }

    public void play()
    {
        log = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            playRound(i);
            if(user1.getDeck().checkIfCollectionIsEmpty() || user2.getDeck().checkIfCollectionIsEmpty())
            {
                break;
            }
        }
        if(user1.getDeck().checkIfCollectionIsEmpty()){
            setUserStatsWinner(user2);
            setUserStatsLoser(user1);
        } else if(user2.getDeck().checkIfCollectionIsEmpty()) {
            setUserStatsLoser(user2);
            setUserStatsWinner(user1);
        }

        printLog();
        System.out.println("User 1:");
        user1.getDeck().printCollection();
        System.out.println("User 2:");
        user2.getDeck().printCollection();
    }

    public void playRound(int i)
    {
        Fight fight = new Fight();
        String logLine = fight.fightOneRound(user1, user2);
        logLine = "Round " + i + ": " + logLine;
        log.add(logLine);
    }

    public String getLogAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String logLine : log) {
            stringBuilder.append(logLine);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private void setUserStatsWinner(User user) {
        Integer newElo = user.getUserStats().getElo() + 3;
        user.getUserStats().setElo(newElo);
        Integer newWins = user.getUserStats().getWins() + 1;
        user.getUserStats().setWins(newWins);
    }

    private void setUserStatsLoser(User user) {
        Integer newElo = user.getUserStats().getElo() - 5;
        user.getUserStats().setElo(newElo);
        Integer newLosses = user.getUserStats().getLosses() + 1;
        user.getUserStats().setLosses(newLosses);
    }
}
