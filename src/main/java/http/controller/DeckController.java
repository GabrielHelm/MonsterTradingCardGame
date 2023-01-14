package http.controller;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static game.router.RouteIdentifier.routeIdentifier;

public class DeckController implements Controller {

    AuthenticateController authenticateController;

    UserCardsRepository userCardsRepository;

    CardRepository cardRepository;

    public DeckController(AuthenticateController authenticateController, UserCardsRepository userCardsRepository, CardRepository cardRepository) {
        this.authenticateController = authenticateController;
        this.userCardsRepository = userCardsRepository;
        this.cardRepository = cardRepository;
    }


    public Response getCardsFromDeck(RequestContext requestContext) {

        String username = authenticateController.Authenticate(requestContext);

        // get cards from user deck
        List<String> cardIds = userCardsRepository.getAllCardIdsFromUserDeck(username);

        // user deck has no cards response
        if(cardIds.isEmpty()) {
            return new Response(HttpStatus.NO_CONTENT, "The request was fine, but the deck doesn't have any cards");
        }
        // get cards from cardRepository
        CardCollection cardCollection = new CardCollection();
        for(String cardId : cardIds) {
            cardCollection.addCardToCollection(cardRepository.getCard(cardId));
        }
        System.out.println(requestContext.getSubpath());

        if(requestContext.getSubpath() == null || requestContext.getSubpath().isEmpty()) {
            // response with cards as JSON
            return new Response(HttpStatus.OK, cardCollection.getCards());
        } else {
            // response with cards as plainText
            Set<String> cardDataAsPlainText = new HashSet<>();

            for(Card card : cardCollection.getCards()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Name:");
                stringBuilder.append(card.getName());
                stringBuilder.append(" Damage:");
                stringBuilder.append(card.getDamage());
                stringBuilder.append(" Id:");
                stringBuilder.append(card.getId());
                cardDataAsPlainText.add(stringBuilder.toString());
            }
            String cardsDataAsPlainText = String.join(" | ", cardDataAsPlainText);
            return new Response(HttpStatus.OK, cardsDataAsPlainText);
        }
    }

    public Response configureDeck(RequestContext requestContext) {


        String username = authenticateController.Authenticate(requestContext);

        // Convert body to List
        List<String> cardIds = requestContext.getBodyAs(List.class);

        if(cardIds.size() != 4) {
            return new Response(HttpStatus.BAD_REQUEST, "The provided deck did not include the required amount of cards");
        }

        // get cardIds from cardRepository
        List<String> cardIdsFromRepo = userCardsRepository.getAllCardIdsFromAvailableUserCards(username);
        List<String> cardIdsFromOldDeck = userCardsRepository.getAllCardIdsFromUserDeck(username);
        cardIds.retainAll(cardIdsFromRepo);
        if(cardIds.size() != 4) {
            return new Response(HttpStatus.FORBIDDEN, "At least one of the provided cards does not belong to the user or is not available.");
        }

        for (String cardId : cardIdsFromOldDeck) {
            userCardsRepository.updateCardChangeToStack(cardId, username);
        }

        for(String cardId : cardIds) {
            userCardsRepository.updateCardChangeToDeck(cardId, username);
        }
        return new Response(HttpStatus.OK, "The deck has been successfully configured");
    }

    @Override
    public List<Pair<RouteIdentifier, Route>> listRoutes() {
        List<Pair<RouteIdentifier, Route>> packageRoutes = new ArrayList<>();

        packageRoutes.add(new Pair<>(
                routeIdentifier("/deck", "GET"),
                this::getCardsFromDeck
        ));

        packageRoutes.add(new Pair<>(
                routeIdentifier("/deck", "PUT"),
                this::configureDeck
        ));

        return packageRoutes;
    }
}
