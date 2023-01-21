package http.server;

import game.router.Route;
import game.router.RouteIdentifier;
import game.router.Router;
import http.header.Header;
import http.header.HeaderParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private final Router router;

    public HttpServer(Router router) {
        this.router = router;
    }
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

                        final RouteIdentifier routeIdentifier = new RouteIdentifier(
                                requestContext.getPath(),
                                requestContext.getHttpVerb()
                        );
                        final Route route = router.findRoute(routeIdentifier);
                        Response response;
                        if(route == null) {
                            response = new Response();
                            response.setBody("Route not found");
                            response.setHttpStatus(HttpStatus.BAD_REQUEST);
                        } else {
                            try {
                                response = route.process(requestContext);
                            } catch (BadRequestException badRequestException) {
                                response = new Response();
                                response.setBody(badRequestException.getMessage());
                                response.setHttpStatus(HttpStatus.BAD_REQUEST);

                            } catch (UnauthorizedException e) {
                                response = new Response();
                                response.setBody(e.getMessage());
                                response.setHttpStatus(HttpStatus.UNAUTHORIZED);
                            } catch (IllegalStateException e) {
                                response = new Response();
                                response.setBody(e.getMessage());
                                response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                            }
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

}
