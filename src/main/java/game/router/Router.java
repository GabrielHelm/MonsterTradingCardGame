package game.router;

import game.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<RouteIdentifier, Route> routes = new HashMap<>();

    public void registerRoute(Pair<RouteIdentifier, Route> routeIdentifierRoutePair) {
        routes.put(routeIdentifierRoutePair.left(), routeIdentifierRoutePair.right());
    }

    public Route findRoute(RouteIdentifier routeIdentifier) {
        return routes.get(routeIdentifier);
    }


}
