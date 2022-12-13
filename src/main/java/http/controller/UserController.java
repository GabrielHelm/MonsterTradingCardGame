package http.controller;

import Game.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import http.HttpStatus;
import http.Response;

public class UserController {

    public Response register(String registerData) throws JsonProcessingException {
        User user = new ObjectMapper().readValue(registerData, User.class);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        // Postgres Aufruf

        Response response;
        // erfolgreich User erstellt
        response = new Response(HttpStatus.CREATED);
        // nicht erfolgreich - User existiert schon
        response = new Response(HttpStatus.BAD_REQUEST);
        return response;
    }

    public Response login(String loginData) throws JsonProcessingException {
        User user = new ObjectMapper().readValue(loginData, User.class);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        Response response;
        // User erfolgreich eingeloggt
            user.generateToken();
            // Token in Postgres speichern
            response = new Response(HttpStatus.CREATED, "\"Token\":\"" + user.getToken() + "\"");
        // nicht erfolgreich
            //response = new Response(HttpStatus.FORBIDDEN);
        return response;
    }

}
