package game.ParsingClasses;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UserStatsSimulator {
    private String name;
    private Integer wins;
    private Integer losses;
    private Integer draw;
    private double winLossPercentage;

    @JsonCreator
    public UserStatsSimulator(UserStats userStats) {
        this.name = userStats.getName();
        this.wins = userStats.getWins();
        this.losses = userStats.getLosses();
        this.draw = (10 - (wins + losses));
        int games = wins + losses + draw;
        this.winLossPercentage = ((wins + 0.5 * draw) / games) * 100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public double getWinLossPercentage() {
        return winLossPercentage;
    }

    public void setWinLossPercentage(Integer winLossPercentage) {
        this.winLossPercentage = winLossPercentage;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }
}
