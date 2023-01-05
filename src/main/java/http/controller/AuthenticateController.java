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
        String tokenName = requestContext.getToken();
        Token token = tokenRepository.getTokenFromTokenName(tokenName);
        // check if token exists and is valid
        if(token != null && token.getValid_until().after(new Timestamp(System.currentTimeMillis()))) {
            return tokenRepository.getUsernameFromTokenName(tokenName);
        } else {
            throw new UnauthorizedException("Access token is missing or invalid");
        }
    }
}
