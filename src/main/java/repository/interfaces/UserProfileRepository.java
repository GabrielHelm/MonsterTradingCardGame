package repository.interfaces;

import game.UserProfile;
import game.UserStats;

import java.util.List;

public interface UserProfileRepository {

    void addUserProfile(String username, UserProfile userProfile);

    void updateUserProfile(String username, UserProfile userProfile);

    UserProfile getUserProfile(String username);

    UserStats getStats(String username);

    List<UserStats> getScoreboard();
}
