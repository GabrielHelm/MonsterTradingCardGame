package game;

import game.router.Router;
import http.controller.PackageController;
import http.controller.UserController;
import http.server.HttpServer;

public class Main {
    public static void main(String[] args) {

        UserController userController = new UserController();
        PackageController packageController = new PackageController();

        Router router = new Router();
        userController.listRoutes()
                .forEach(router::registerRoute);

        packageController.listRoutes()
                .forEach(router::registerRoute);

        HttpServer httpServer = new HttpServer(router);
        httpServer.start();
    }
}