package http.controller;

import game.Credentials;
import game.Token;
import game.User;
import game.router.Controller;
import game.router.Route;
import game.router.RouteIdentifier;
import game.util.Pair;
import http.server.BadRequestException;
import http.server.HttpStatus;
import http.server.RequestContext;
import http.server.Response;
import repository.TokenRepositoryImpl;
import repository.UserRepositoryImpl;
import repository.db.config.DatabaseConnection;
import repository.interfaces.TokenRepository;
import repository.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static game.router.RouteIdentifier.routeIdentifier;

public class UserController implements Controller {

    private UserRepository userRepository
            = new UserRepositoryImpl(DatabaseConnection.getInstance());
    private TokenRepository tokenRepository
            = new TokenRepositoryImpl(DatabaseConnection.getInstance());

    public Response register(RequestContext requestContext) {
        Credentials credentials = requestContext.getBodyAs(Credentials.class);
        Response response;

        User user = userRepository.findUserByUsername(credentials.getUsername());
        if (user != null) {
            throw new BadRequestException("User with username " + credentials.getUsername() + " already exists");
        } else {
            userRepository.create(credentials);
            response = new Response(HttpStatus.CREATED);
        }

        return response;
    }

    public Response login(RequestContext requestContext) {
        Response response;
        Credentials credentials = requestContext.getBodyAs(Credentials.class);

        if (userRepository.checkCredentials(credentials)) {
            User user = userRepository.findUserByUsername(credentials.getUsername());
            user.generateToken();
            Token token = tokenRepository.getTokenFromTokenName(user.getToken().getName());
            if (token == null) {
                tokenRepository.createTokenForUser(user);
            } else {
                tokenRepository.updateTokenTimestamp(token);
            }

            response = new Response(HttpStatus.OK, "\"Token\": \"Basic " + user.getToken().getName() + "\"");
        } else {
            // nicht erfolgreich
            // set WWW-Authenticate header
            response = new Response(HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    @Override
    public List<Pair<RouteIdentifier, Route>> listRoutes() {
        List<Pair<RouteIdentifier, Route>> userRoutes = new ArrayList<>();

        userRoutes.add(new Pair<>(
                routeIdentifier("/users", "POST"),
                this::register
        ));

        userRoutes.add(new Pair<>(
                routeIdentifier("/sessions", "POST"),
                this::login
        ));
        return userRoutes;
    }

}
