package repository.interfaces;

import game.Credentials;
import game.User;

public interface UserRepository {
    void create(Credentials credentials);
    boolean checkCredentials(Credentials credentials);

    User findUserByUsername(String username);

    void updateCoinsForUser(Integer coins, String username);
}