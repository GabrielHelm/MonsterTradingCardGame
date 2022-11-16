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
        for (int i = 0; i < 4; i++) {
            playRound(user1.getDeck().getRandomCardFromCollection(), user2.getDeck().getRandomCardFromCollection());
        }
        printLog();
    }

    public void playRound(Card card1, Card card2)
    {
        User user1 = getUser1();
        User user2 = getUser2();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(user1.getUsername());
        stringBuilder.append(": ");
        stringBuilder.append(card1.getName());
        stringBuilder.append(" (");
        stringBuilder.append(card1.getDamage());
        stringBuilder.append(") Damage vs. ");

        stringBuilder.append(user2.getUsername());
        stringBuilder.append(": ");
        stringBuilder.append(card2.getName());
        stringBuilder.append(" (");
        stringBuilder.append(card2.getDamage());
        stringBuilder.append(") Damage");

        log.add(stringBuilder.toString());
    }
}
