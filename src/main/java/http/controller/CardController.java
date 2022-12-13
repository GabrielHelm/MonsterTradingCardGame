package http.controller;

import Game.Card.CardCollection;
import Game.Card.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import http.HttpStatus;
import http.Response;

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
