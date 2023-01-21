package game.router;

import game.util.Pair;

import java.util.List;

public interface Controller {
    List<Pair<RouteIdentifier, Route>> listRoutes();
}
