package http.controller;

import game.UserStats;
import game.router.Controller;
import game.router.Route;
import game.router.RouteIdentifier;
import game.util.Pair;
import http.server.HttpStatus;
import http.server.RequestContext;
import http.server.Response;
import repository.interfaces.UserCardsRepository;
import repository.interfaces.UserProfileRepository;

import java.util.ArrayList;
import java.util.List;

import static game.router.RouteIdentifier.routeIdentifier;

public class GameController implements Controller {
    private UserCardsRepository userCardsRepository;
    private AuthenticateController authenticateController;

    private UserProfileRepository userProfileRepository;

    public GameController(UserCardsRepository userCardsRepository, AuthenticateController authenticateController, UserProfileRepository userProfileRepository) {
        this.userCardsRepository = userCardsRepository;
        this.authenticateController = authenticateController;
        this.userProfileRepository = userProfileRepository;
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


        return new Response(HttpStatus.OK, "okay");
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
