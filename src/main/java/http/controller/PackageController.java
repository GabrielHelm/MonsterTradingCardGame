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
import repository.CardRepositoryImpl;
import repository.PackageRepositoryImpl;
import repository.db.config.DatabaseConnection;
import repository.interfaces.CardRepository;
import repository.interfaces.PackageRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static game.router.RouteIdentifier.routeIdentifier;

public class PackageController implements Controller {
    private CardRepository cardRepository
            = new CardRepositoryImpl(DatabaseConnection.getInstance());

    private PackageRepository packageRepository
            = new PackageRepositoryImpl(DatabaseConnection.getInstance());

    private AuthenticateController authenticateController = new AuthenticateController();
    public Response createPackage(RequestContext requestContext) {

        Response response;

        String username = authenticateController.Authenticate(requestContext);

        String uniqueID = UUID.randomUUID().toString();

        List<Card> cards = Arrays.asList(requestContext.getBodyAs(Card[].class));

        CardCollection cardCollection = new CardCollection();
        cardCollection.setU_ID(uniqueID);
        cardCollection.setCards(cards);
        cardCollection.printCollection();
        System.out.println("UID = " + cardCollection.getU_ID());

        for (Card card : cards) {
            Card cardFromDB = cardRepository.getCard(card.getId());
            if(cardFromDB == null) {
                cardRepository.createCard(card);
            }
        }
        packageRepository.createPackage(cardCollection);

        response = new Response(HttpStatus.CREATED);
        return response;
    }

    public Response getPackage(RequestContext requestContext) {
        // Authentication Method - get Username in return

        // check if User has enough coins

        Response response = new Response(HttpStatus.OK);

        String username = authenticateController.Authenticate(requestContext);
        System.out.println(username);

        List<String> cardIDs = packageRepository.getCardIdsFromRandomPackage();

        System.out.println(cardIDs);
        // Postgres get random PackageID

        // get cards from Package
        //CardRepository cardRepository = new CardRepository();
        //CardCollection cardCollection = cardRepository.getPackageFromCollection();

        // change owner of acquired cards

        //Creating the ObjectMapper object
        //Converting the Object to JSONString

        //response = new Response(HttpStatus.OK, cardCollection.getCards());
        // if no Package available or not enough coins
        //response = new Response(HttpStatus.BAD_REQUEST);

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
