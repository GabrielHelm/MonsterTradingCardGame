package http.controller;

import game.ParsingClasses.Credentials;
import game.Token;
import game.User;
import game.ParsingClasses.UserProfile;
import game.router.Controller;
import game.router.Route;
import game.router.RouteIdentifier;
import game.util.Pair;
import http.server.BadRequestException;
import http.server.HttpStatus;
import http.server.RequestContext;
import http.server.Response;
import repository.interfaces.TokenRepository;
import repository.interfaces.UserProfileRepository;
import repository.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static game.router.RouteIdentifier.routeIdentifier;

public class UserController implements Controller {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private UserProfileRepository userProfileRepository;
    private AuthenticateController authenticateController;

    public UserController(UserRepository userRepository, TokenRepository tokenRepository, UserProfileRepository userProfileRepository, AuthenticateController authenticateController) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.authenticateController = authenticateController;
        this.userProfileRepository = userProfileRepository;
    }

    public Response register(RequestContext requestContext) {
        Credentials credentials = requestContext.getBodyAs(Credentials.class);

        User user = userRepository.findUserByUsername(credentials.getUsername());
        if (user != null) {
            throw new BadRequestException("User with username " + credentials.getUsername() + " already exists");
        } else {
            userRepository.create(credentials);
            UserProfile userProfile = new UserProfile();
            userProfileRepository.addUserProfile(credentials.getUsername(), userProfile);
            return new Response(HttpStatus.CREATED, "User successfully created");
        }
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
            response = new Response(HttpStatus.UNAUTHORIZED, "Invalid username/password provided");
        }
        return response;
    }

    public Response getUserProfile(RequestContext requestContext) {

        String username = authenticateController.Authenticate(requestContext);

        if(!username.equals(requestContext.getSubpath())) {
            return new Response(HttpStatus.NOT_FOUND, "User not found");
        }

        UserProfile userProfile = userProfileRepository.getUserProfile(username);
        return new Response(HttpStatus.OK, userProfile);
    }

    public Response updateUserProfile(RequestContext requestContext) {

        String username = authenticateController.Authenticate(requestContext);

        if(!username.equals(requestContext.getSubpath())) {
            return new Response(HttpStatus.NOT_FOUND, "User not found");
        }

        UserProfile userProfile = requestContext.getBodyAs(UserProfile.class);
        userProfileRepository.updateUserProfile(username, userProfile);
        return new Response(HttpStatus.OK, "User sucessfully updated");
    }

    @Override
    public List<Pair<RouteIdentifier, Route>> listRoutes() {
        List<Pair<RouteIdentifier, Route>> userRoutes = new ArrayList<>();

        userRoutes.add(new Pair<>(
                routeIdentifier("/users", "POST"),
                this::register
        ));

        userRoutes.add(new Pair<>(
                routeIdentifier("/users/", "GET"),
                this::getUserProfile
        ));

        userRoutes.add(new Pair<>(
                routeIdentifier("/users/", "PUT"),
                this::updateUserProfile
        ));

        userRoutes.add(new Pair<>(
                routeIdentifier("/sessions", "POST"),
                this::login
        ));
        return userRoutes;
    }

}
