package http.controller;

import game.card.CardCollection;
import game.card.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import http.server.HttpStatus;
import http.server.Response;

public class CardController {
    public Response getCards() throws JsonProcessingException {

        // Authentication Method - get Username in return

        Response response;
        // get cards from User
        CardRepository cardRepository = new CardRepository();
        CardCollection cardCollection = cardRepository.getPackageFromCollection();

        //Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(cardCollection.getCards());

        response = new Response(HttpStatus.OK, jsonString);

        return response;
    }
}
