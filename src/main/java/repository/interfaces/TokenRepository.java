package repository.interfaces;

import game.Token;
import game.User;


public interface TokenRepository {
    void createTokenForUser(User user);
    Token getTokenFromTokenName(String token);
    void updateTokenTimestamp(Token token);
    String getUsernameFromTokenName(String tokenName);
}
