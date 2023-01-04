package http.controller;

import game.Token;
import http.server.BadRequestException;
import http.server.RequestContext;
import http.server.UnauthorizedException;
import repository.TokenRepositoryImpl;
import repository.db.config.DatabaseConnection;
import repository.interfaces.TokenRepository;

import java.sql.Timestamp;

public class AuthenticateController {

    private TokenRepository tokenRepository
            = new TokenRepositoryImpl(DatabaseConnection.getInstance());

    public String Authenticate(RequestContext requestContext) {
        String tokenname = requestContext.getToken();
        Token token = tokenRepository.getTokenFromTokenName(tokenname);
        if(token != null && token.getValid_until().after(new Timestamp(System.currentTimeMillis()))) {
            return tokenRepository.getUsernameFromTokenName(tokenname);
        } else {
            throw new UnauthorizedException("Authorization failed");
        }
    }
}
