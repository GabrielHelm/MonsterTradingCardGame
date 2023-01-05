package game;

import game.router.Router;
import http.controller.*;
import http.server.HttpServer;
import repository.*;
import repository.db.config.DatabaseConnection;
import repository.interfaces.*;

public class Main {
    public static void main(String[] args) {

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        CardRepository cardRepository = new CardRepositoryImpl(databaseConnection);
        UserRepository userRepository = new UserRepositoryImpl(databaseConnection);
        TokenRepository tokenRepository = new TokenRepositoryImpl(databaseConnection);
        PackageRepository packageRepository = new PackageRepositoryImpl(databaseConnection);
        UserCardsRepository userCardsRepository = new UserCardsRepositoryImpl(databaseConnection);
        UserProfileRepository userProfileRepository = new UserProfileRepositoryImpl(databaseConnection);
        AuthenticateController authenticateController = new AuthenticateController();

        UserController userController = new UserController(userRepository, tokenRepository, userProfileRepository, authenticateController);
        PackageController packageController = new PackageController(cardRepository, packageRepository, userRepository, userCardsRepository, authenticateController);
        CardController cardController = new CardController(userCardsRepository, cardRepository, authenticateController);
        DeckController deckController = new DeckController(authenticateController, userCardsRepository, cardRepository);
        GameController gameController = new GameController(userCardsRepository, authenticateController, userProfileRepository);

        Router router = new Router();
        userController.listRoutes()
                .forEach(router::registerRoute);

        packageController.listRoutes()
                .forEach(router::registerRoute);

        cardController.listRoutes()
                .forEach(router::registerRoute);

        deckController.listRoutes()
                .forEach(router::registerRoute);

        gameController.listRoutes()
                .forEach(router::registerRoute);

        HttpServer httpServer = new HttpServer(router);
        httpServer.start();
    }
}