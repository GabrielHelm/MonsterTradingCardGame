package http.controller;

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

import java.util.ArrayList;
import java.util.List;

import static game.router.RouteIdentifier.routeIdentifier;

public class CardController implements Controller {

    private UserCardsRepository userCardsRepository;

    private CardRepository cardRepository;

    private AuthenticateController authenticateController;

    public CardController(UserCardsRepository userCardsRepository, CardRepository cardRepository, AuthenticateController authenticateController) {
        this.userCardsRepository = userCardsRepository;
        this.cardRepository = cardRepository;
        this.authenticateController = authenticateController;
    }

    public Response getCards(RequestContext requestContext) {

        Response response;
        String username = authenticateController.Authenticate(requestContext);

        // get cards from user stack
        List<String> cardIds = userCardsRepository.getAllCardIdsFromUserCards(username);
        // user has no cards response
        if(cardIds.isEmpty()) {
            return new Response(HttpStatus.NO_CONTENT, "The request was fine, but the user doesn't have any cards");
        }
        // get cards from cardRepository
        CardCollection cardCollection = new CardCollection();
        for(String cardId : cardIds) {
            cardCollection.addCardToCollection(cardRepository.getCard(cardId));
        }
        // response with cards as JSON
        response = new Response(HttpStatus.OK, cardCollection.getCards());

        return response;
    }

    @Override
    public List<Pair<RouteIdentifier, Route>> listRoutes() {
        List<Pair<RouteIdentifier, Route>> packageRoutes = new ArrayList<>();

        packageRoutes.add(new Pair<>(
                routeIdentifier("/cards", "GET"),
                this::getCards
        ));

        return packageRoutes;
    }
}
