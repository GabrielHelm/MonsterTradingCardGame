package http.controller;

import game.ParsingClasses.TradingDeal;
import game.card.Card;
import game.router.Controller;
import game.router.Route;
import game.router.RouteIdentifier;
import game.util.Pair;
import http.server.HttpStatus;
import http.server.RequestContext;
import http.server.Response;
import repository.interfaces.CardRepository;
import repository.interfaces.TradingRepository;
import repository.interfaces.UserCardsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static game.router.RouteIdentifier.routeIdentifier;

public class TradingController implements Controller {
    private AuthenticateController authenticateController;

    private TradingRepository tradingRepository;

    private UserCardsRepository userCardsRepository;

    private CardRepository cardRepository;

    public TradingController(AuthenticateController authenticateController, TradingRepository tradingRepository, UserCardsRepository userCardsRepository, CardRepository cardRepository) {
        this.authenticateController = authenticateController;
        this.tradingRepository = tradingRepository;
        this.userCardsRepository = userCardsRepository;
        this.cardRepository = cardRepository;
    }

    public Response getTradingDeals(RequestContext requestContext) {

        String username = authenticateController.authenticate(requestContext);

        List<TradingDeal> tradingDeals = tradingRepository.getTradingDeals(username);

        if(tradingDeals.isEmpty()) {
            return new Response(HttpStatus.NO_CONTENT, "The request was fine, but there are no trading deals available");
        }
        return new Response(HttpStatus.OK, tradingDeals);
    }

    public Response createTradingDeal(RequestContext requestContext) {

        String username = authenticateController.authenticate(requestContext);

        TradingDeal tradingDeal = requestContext.getBodyAs(TradingDeal.class);

        TradingDeal tradingDealFromRepository = tradingRepository.getTradingDeal(tradingDeal.getId());
        if(tradingDealFromRepository != null) {
            return new Response(HttpStatus.CONFLICT, "A deal with this deal ID already exists.");
        }

        if(userCardsRepository.checkIfCardIsInUserStackAndAvailable(tradingDeal.getCardToTrade(), username)) {
            userCardsRepository.updateCardChangeToNotAvailable(tradingDeal.getCardToTrade(), username);
        } else {
            return new Response(HttpStatus.FORBIDDEN, "The deal contains a card that is not owned by the user or locked in the deck/another trading deal.");
        }

        tradingRepository.createTradingDeal(username, tradingDeal);

        return new Response(HttpStatus.CREATED, "Trading deal successfully created");
    }

    public Response deleteTradingDeal(RequestContext requestContext) {

        String username = authenticateController.authenticate(requestContext);

        TradingDeal tradingDeal = tradingRepository.getTradingDeal(requestContext.getSubpath());
        if(tradingDeal == null) {
            return new Response(HttpStatus.NOT_FOUND, "The provided deal ID was not found.");
        }

        List<String> tradingDealsIds = tradingRepository.getTradingDealsIdsFromUser(username);

        if(!(tradingDealsIds.contains(tradingDeal.getId()))) {
            return new Response(HttpStatus.FORBIDDEN, "User is not the owner of this deal.");
        }
        userCardsRepository.updateCardChangeToAvailable(tradingDeal.getCardToTrade(), username);
        tradingRepository.deleteTradingDeal(tradingDeal.getId());

        return new Response(HttpStatus.OK,"Trading deal successfully deleted");
    }

    public Response acceptTradingDeal(RequestContext requestContext) {

        String username = authenticateController.authenticate(requestContext);
        String tradingDealCardId = requestContext.getBodyAs(String.class);
        String tradingDealId = requestContext.getSubpath();

        TradingDeal tradingDeal = tradingRepository.getTradingDeal(tradingDealId);
        if(tradingDeal == null) {
            return new Response(HttpStatus.NOT_FOUND, "The provided deal ID was not found.");
        }
        String tradingDealOwner = tradingRepository.getTradingDealOwner(tradingDealId);
        if(Objects.equals(username, tradingDealOwner)) {
            return new Response(HttpStatus.BAD_REQUEST, "You cannot trade with yourself.");
        }

        List<String> cardIdsFromUser = userCardsRepository.getAllCardIdsFromAvailableUserStack(username);

        if(!(cardIdsFromUser.contains(tradingDealCardId))) {
            return new Response(HttpStatus.FORBIDDEN, "The offered card is not owned by the user, or the requirements are not met (Type, MinimumDamage), or the offered card is locked in the deck.");
        }

        Card offeredCard = cardRepository.getCard(tradingDealCardId);
        if(offeredCard.getCardType() != tradingDeal.getType() || offeredCard.getDamage() <= tradingDeal.getMinimumDamage()) {
            return new Response(HttpStatus.FORBIDDEN, "The offered card is not owned by the user, or the requirements are not met (Type, MinimumDamage), or the offered card is locked in the deck.");
        }

        userCardsRepository.updateCardChangeUser(tradingDealCardId, tradingDealOwner);
        userCardsRepository.updateCardChangeUser(tradingDeal.getCardToTrade(), username);
        userCardsRepository.updateCardChangeToAvailable(tradingDeal.getCardToTrade(),username);
        tradingRepository.deleteTradingDeal(tradingDealId);
        return new Response(HttpStatus.OK, "Trading deal successfully executed.");
    }

    public List<Pair<RouteIdentifier, Route>> listRoutes() {
        List<Pair<RouteIdentifier, Route>> packageRoutes = new ArrayList<>();

        packageRoutes.add(new Pair<>(
                routeIdentifier("/tradings", "GET"),
                this::getTradingDeals
        ));

        packageRoutes.add(new Pair<>(
                routeIdentifier("/tradings", "POST"),
                this::createTradingDeal
        ));

        packageRoutes.add(new Pair<>(
                routeIdentifier("/tradings/", "DELETE"),
                this::deleteTradingDeal
        ));

        packageRoutes.add(new Pair<>(
                routeIdentifier("/tradings/", "POST"),
                this::acceptTradingDeal
        ));


        return packageRoutes;
    }
}
