package http.controller;

import game.Game;
import game.ParsingClasses.UserStats;
import game.ParsingClasses.UserStatsSimulator;
import game.User;
import game.card.CardCollection;
import game.router.Controller;
import game.router.Route;
import game.router.RouteIdentifier;
import game.util.Pair;
import http.server.BadRequestException;
import http.server.HttpStatus;
import http.server.RequestContext;
import http.server.Response;
import repository.interfaces.CardRepository;
import repository.interfaces.UserCardsRepository;
import repository.interfaces.UserProfileRepository;
import repository.interfaces.UserRepository;

import java.util.*;

import static game.router.RouteIdentifier.routeIdentifier;

public class GameController implements Controller {
    private UserCardsRepository userCardsRepository;
    private AuthenticateController authenticateController;
    private UserProfileRepository userProfileRepository;
    private UserRepository userRepository;
    private CardRepository cardRepository;

    Map<String, String> gameResults;
    private final List<User> usersBattleQueue = Collections.synchronizedList(new ArrayList<>());


    public GameController(UserCardsRepository userCardsRepository, AuthenticateController authenticateController, UserProfileRepository userProfileRepository, UserRepository userRepository, CardRepository cardRepository) {
        this.userCardsRepository = userCardsRepository;
        this.authenticateController = authenticateController;
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        gameResults = new HashMap<>();
    }

    public Response getStats(RequestContext requestContext) {

        String username = authenticateController.authenticate(requestContext);

        UserStats userStats = userProfileRepository.getStats(username);

        return new Response(HttpStatus.OK, userStats);
    }

    public Response getScoreboard(RequestContext requestContext) {

        authenticateController.authenticate(requestContext);

        List<UserStats> userScoreboard = userProfileRepository.getScoreboard();

        return new Response(HttpStatus.OK, userScoreboard);
    }

    public Response battle(RequestContext requestContext) {

        String username = authenticateController.authenticate(requestContext);

        User user = userRepository.findUserByUsername(username);

        UserStats userStats = userProfileRepository.getStats(username);
        CardCollection userDeck = getUserCards(user);
        userDeck.printCollection();
        user.setDeck(userDeck);
        user.setUserStats(userStats);

        usersBattleQueue.add(user);
        String log = "";

        if(handleBattleQueue()) {
            log = gameResults.remove(username);
        }

        return new Response(HttpStatus.OK, log);
    }

    public Response battleSimulator(RequestContext requestContext) {

        String username = authenticateController.authenticate(requestContext);
        Map<String, String> enemyUsernameMap = requestContext.getBodyAs(Map.class);
        String enemyUsername = enemyUsernameMap.get("Username");

        User user = userRepository.findUserByUsername(username);
        User enemyUser = userRepository.findUserByUsername(enemyUsername);

        UserStats userStats = new UserStats(username, 100, 0, 0);
        UserStats enemyUserStats = new UserStats(enemyUsername, 100, 0, 0);
        user.setUserStats(userStats);
        enemyUser.setUserStats(enemyUserStats);

        for (int i = 0; i < 10; i++) {
            Game game = new Game();
            CardCollection userDeck = getUserCards(user);
            CardCollection enemyUserDeck = getUserCards(enemyUser);
            user.setDeck(userDeck);
            enemyUser.setDeck(enemyUserDeck);

            game.setUser1(user);
            game.setUser2(enemyUser);

            game.play();
        }
        UserStatsSimulator userStatsSimulator = new UserStatsSimulator(userStats);

        return new Response(HttpStatus.OK, userStatsSimulator);
    }

    private CardCollection getUserCards(User user) {
        List<String> cardIds = userCardsRepository.getAllCardIdsFromUserDeck(user.getCredentials().getUsername());
        if(cardIds.isEmpty()) {
            throw new BadRequestException("No deck configured");
        }
        // get cards from cardRepository
        CardCollection userDeck = new CardCollection();
        for(String cardId : cardIds) {
            userDeck.addCardToCollection(cardRepository.getCard(cardId));
        }
        return userDeck;
    }

    private synchronized boolean handleBattleQueue() {
        if(usersBattleQueue.size() == 1) {
            try {
                this.wait();
                return true;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } else {
            User user1 = usersBattleQueue.remove(0);
            User user2 = usersBattleQueue.remove(0);
            System.out.println("Battle between " + user1.getCredentials().getUsername() + " : " + user2.getCredentials().getUsername());
            Game game = new Game();
            game.setUser1(user1);
            game.setUser2(user2);
            game.play();

            userProfileRepository.updateUserStats(user1.getCredentials().getUsername(), user1.getUserStats());
            userProfileRepository.updateUserStats(user2.getCredentials().getUsername(), user2.getUserStats());

            gameResults.put(user1.getCredentials().getUsername(), game.getLogAsString());
            gameResults.put(user2.getCredentials().getUsername(), game.getLogAsString());

            notify();

            return true;
        }
    }

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
        packageRoutes.add(new Pair<>(
                routeIdentifier("/battles/simulator", "POST"),
                this::battleSimulator
        ));

        return packageRoutes;
    }
}
