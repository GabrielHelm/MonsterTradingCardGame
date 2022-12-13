package Game;

import http.HttpServer;

public class Main {
    public static void main(String[] args) {

        HttpServer httpServer = new HttpServer();

        httpServer.start();
    }
}