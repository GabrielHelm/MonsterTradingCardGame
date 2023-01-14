package http.controller;

import game.Game;
import game.ParsingClasses.UserStats;
import game.User;
import game.card.Card;
import game.card.CardCollection;
import game.router.Controller;
import game.router.Route;
import game.router.RouteIdentifier;
import game.util.Pair;
import http.server.HttpStatus;
import http.server.RequestContext;
import http.server.Response;
import repository.interfaces.CardRepository;
import repository.interfaces.UserCardsRepository;
import repository.interfaces.UserProfileRepository;
import repository.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static game.router.RouteIdentifier.routeIdentifier;

public class GameController implements Controller {
    private UserCardsRepository userCardsRepository;
    private AuthenticateController authenticateController;
    private UserProfileRepository userProfileRepository;
    private UserRepository userRepository;
    private CardRepository cardRepository;

    private Game game;

    private final List<User> usersBattleQueue = Collections.synchronizedList(new ArrayList<>());


    public GameController(UserCardsRepository userCardsRepository, AuthenticateController authenticateController, UserProfileRepository userProfileRepository, UserRepository userRepository, CardRepository cardRepository) {
        this.userCardsRepository = userCardsRepository;
        this.authenticateController = authenticateController;
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        game = new Game();
    }

    public Response getStats(RequestContext requestContext) {

        String username = authenticateController.Authenticate(requestContext);

        UserStats userStats = userProfileRepository.getStats(username);

        return new Response(HttpStatus.OK, userStats);
    }

    public Response getScoreboard(RequestContext requestContext) {

        String username = authenticateController.Authenticate(requestContext);

        List<UserStats> userScoreboard = userProfileRepository.getScoreboard();

        return new Response(HttpStatus.OK, userScoreboard);
    }

    public Response battle(RequestContext requestContext) {

        String username = authenticateController.Authenticate(requestContext);

        User user = userRepository.findUserByUsername(username);

        UserStats userStats = userProfileRepository.getStats(username);
        List<String> cardIds = userCardsRepository.getAllCardIdsFromUserDeck(username);

        if(cardIds.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, "No deck configured");
        }
        // get cards from cardRepository
        CardCollection userDeck = new CardCollection();
        for(String cardId : cardIds) {
            userDeck.addCardToCollection(cardRepository.getCard(cardId));
        }
        user.setUserStats(userStats);
        user.setDeck(userDeck);

        usersBattleQueue.add(user);

        String log = handleBattleQueue(game);

        return new Response(HttpStatus.OK, log);
    }

    private synchronized String handleBattleQueue(Game game) {
        if(usersBattleQueue.size() == 1) {
            try {
                this.wait();
                return game.getLogAsString();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } else {
            User user1 = usersBattleQueue.remove(0);
            User user2 = usersBattleQueue.remove(0);
            System.out.println("Battle between " + user1.getCredentials().getUsername() + " : " + user2.getCredentials().getUsername());
            game.setUser1(user1);
            game.setUser2(user2);
            game.play();

            userProfileRepository.updateUserStats(user1.getCredentials().getUsername(), user1.getUserStats());
            userProfileRepository.updateUserStats(user2.getCredentials().getUsername(), user2.getUserStats());

            notify();
            return game.getLogAsString();
        }
    }

    @Override
    public List<Pair<RouteIdentifier, Route>> listRoutes() {
        List<Pair<RouteIdentifier, Route>> packageRoutes = new ArrayList<>();

        packageRoutes.add(new Pair<>(
                routeIdentifier("/stats", "GET"),
                this::getStats
        ));

        packageRoutes.add(new Pair<>(
                routeIdentifier("/score", "GET"),
                this::getScoreboard
        ));
        packageRoutes.add(new Pair<>(
                routeIdentifier("/battles", "POST"),
                this::battle
        ));

        return packageRoutes;
    }
}
