package game.router;

import http.server.RequestContext;
import http.server.Response;

public interface Route {
    Response process(RequestContext requestContext);
}
