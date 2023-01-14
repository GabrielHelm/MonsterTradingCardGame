package repository.interfaces;

import game.ParsingClasses.UserProfile;
import game.ParsingClasses.UserStats;

import java.util.List;

public interface UserProfileRepository {

    void addUserProfile(String username, UserProfile userProfile);

    void updateUserProfile(String username, UserProfile userProfile);

    void updateUserStats(String username, UserStats userStats);

    UserProfile getUserProfile(String username);

    UserStats getStats(String username);

    List<UserStats> getScoreboard();
}
