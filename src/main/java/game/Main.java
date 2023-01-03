package game;

import http.server.HttpServer;

public class Main {
    public static void main(String[] args) {

        HttpServer httpServer = new HttpServer();

        httpServer.start();
    }
}