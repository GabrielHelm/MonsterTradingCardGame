package http.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import http.controller.CardController;
import http.controller.DeckController;
import http.controller.PackageController;
import http.controller.UserController;
import http.header.Header;
import http.header.HeaderParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    public void start() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);


        try (ServerSocket serverSocket = new ServerSocket(10001)) {
            while (true) {
                final Socket socket = serverSocket.accept();
                executorService.submit(() -> {
                    try {
                        System.out.println("Thread: " + Thread.currentThread().getName());
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader
                                        (socket.getInputStream()));

                        final RequestContext requestContext = parseInput(br);
                        requestContext.print();
                        Response response;

                        Authentification authentification = new Authentification();
                        if (authentification.authenticate(requestContext.getHeaders()) || "/users".equals(requestContext.getPath()) || "/sessions".equals(requestContext.getPath())) {

                            try{
                                response = routing(requestContext);
                            }catch (BadRequestException badRequestException) {
                                response = new Response(HttpStatus.BAD_REQUEST, badRequestException.getMessage());
                            } catch (IllegalStateException e) {
                                response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                            }
                        } else {
                            // set WWW-Authenticate header
                            response = new Response(HttpStatus.UNAUTHORIZED);
                        }

                        System.out.println(response.toString());
                        BufferedWriter w = new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream()));

                        w.write(response.toString());
                        w.flush();

                        socket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RequestContext parseInput(BufferedReader bufferedReader) throws IOException {
        RequestContext requestContext = new RequestContext();

        String versionString = bufferedReader.readLine();
        final String[] splitVersionString = versionString.split(" ");
        requestContext.setHttpVerb(splitVersionString[0]);
        requestContext.setPath(splitVersionString[1]);

        List<Header> headerList = new ArrayList<>();
        HeaderParser headerParser = new HeaderParser();
        String input;
        do {
            input = bufferedReader.readLine();
            if (input.equals("")) {
                break;
            }
            headerList.add(headerParser.parseHeader(input));
        } while (true);
        requestContext.setHeaders(headerList);

        int contentLength = requestContext.getContentLength();
        char[] buffer = new char[contentLength];
        bufferedReader.read(buffer, 0, contentLength);
        requestContext.setBody(new String(buffer));
        return requestContext;
    }

    public Response routing(RequestContext requestContext) {
        UserController userController;
        PackageController packageController = new PackageController();
        CardController cardController = new CardController();
        DeckController deckController = new DeckController();
        Response response = new Response();
        try {
            switch (requestContext.getPath()) {
                case "/users":
                    userController = new UserController();
                    response = userController.register(requestContext);
                    break;
                case "/sessions":
                    userController = new UserController();
                    response = userController.login(requestContext);
                    break;
                case "/packages":
                    response = packageController.createPackage(requestContext.getBody());
                    break;
                case "/transactions/packages":
                    response = packageController.getPackage();
                    break;
                case "/cards":
                    response = cardController.getCards();
                    break;
                case "/deck":
                    switch (requestContext.getHttpVerb()) {
                        case "GET":
                            response = deckController.getDeck();
                            break;
                        case "PUT":
                            response = deckController.configurateDeck(requestContext.getBody());
                            break;
                    }
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}