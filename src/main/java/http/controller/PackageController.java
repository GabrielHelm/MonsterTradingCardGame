package http.controller;

import game.User;
import game.card.Card;
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
import repository.interfaces.PackageRepository;
import repository.interfaces.UserCardsRepository;
import repository.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static game.router.RouteIdentifier.routeIdentifier;

public class PackageController implements Controller {
    private CardRepository cardRepository;
    private PackageRepository packageRepository;
    private UserRepository userRepository;
    private UserCardsRepository userCardsRepository;
    private AuthenticateController authenticateController;

    public PackageController(CardRepository cardRepository, PackageRepository packageRepository, UserRepository userRepository, UserCardsRepository userCardsRepository, AuthenticateController authenticateController) {
        this.cardRepository = cardRepository;
        this.packageRepository = packageRepository;
        this.userRepository = userRepository;
        this.userCardsRepository = userCardsRepository;
        this.authenticateController = authenticateController;
    }

    public Response createPackage(RequestContext requestContext) {

        Response response;

        String username = authenticateController.Authenticate(requestContext);

        if(!("admin".equals(username))) {
            return new Response(HttpStatus.FORBIDDEN, "Provided user is not admin");
        }

        String uniqueID = UUID.randomUUID().toString();

        List<Card> cards = Arrays.asList(requestContext.getBodyAs(Card[].class));

        CardCollection cardCollection = new CardCollection();
        cardCollection.setU_ID(uniqueID);
        cardCollection.setCards(cards);

        for (Card card : cards) {
            Card cardFromDB = cardRepository.getCard(card.getId());
            if(cardFromDB != null) {
                return new Response(HttpStatus.CONFLICT, "At least one card in the packages already exists");
            }
        }
        for (Card card : cards) {
            cardRepository.createCard(card);
        }

        packageRepository.createPackage(cardCollection);

        response = new Response(HttpStatus.CREATED, "Package and cards successfully created");
        return response;
    }

    public Response getPackage(RequestContext requestContext) {

        Response response;

        String username = authenticateController.Authenticate(requestContext);
        User user = userRepository.findUserByUsername(username);

        if(user.getCoins() < 5) {
            return new Response(HttpStatus.FORBIDDEN, "Not enough money for buying a card package");
        }
        String packageId = packageRepository.getRandomPackageId();
        if(packageId == null) {
            throw new BadRequestException("No card package available for buying");
        }
        userRepository.updateCoinsForUser(user.getCoins() - 5, username);
        List<String> cardIDs = packageRepository.getCardIdsFromPackage(packageId);
        packageRepository.deletePackage(packageId);

        for(String cardId : cardIDs) {
            userCardsRepository.addCardToUserCards(cardId, username);
        }

        response = new Response(HttpStatus.OK, "A package has been successfully bought");
        return response;
    }

    @Override
    public List<Pair<RouteIdentifier, Route>> listRoutes() {
        List<Pair<RouteIdentifier, Route>> packageRoutes = new ArrayList<>();

        packageRoutes.add(new Pair<>(
                routeIdentifier("/packages", "POST"),
                this::createPackage
        ));

        packageRoutes.add(new Pair<>(
                routeIdentifier("/transactions/packages", "POST"),
                this::getPackage
        ));
        return packageRoutes;
    }
}
