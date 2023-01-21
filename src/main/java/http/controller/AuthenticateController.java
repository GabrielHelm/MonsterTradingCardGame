package http.controller;

import game.Token;
import http.server.RequestContext;
import http.server.UnauthorizedException;
import repository.interfaces.TokenRepository;

import java.sql.Timestamp;

public class AuthenticateController {

    public AuthenticateController(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    private TokenRepository tokenRepository;

    public String authenticate(RequestContext requestContext) {
        String tokenName = requestContext.getToken();
        Token token = tokenRepository.getTokenFromTokenName(tokenName);
        // check if token exists and is valid
        if(token != null && token.getValid_until().after(new Timestamp(System.currentTimeMillis()))) {
            tokenRepository.updateTokenTimestamp(token);
            return tokenRepository.getUsernameFromTokenName(tokenName);
        } else {
            throw new UnauthorizedException("Access token is missing or invalid");
        }
    }
}
