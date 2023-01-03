package http.controller;

import game.card.CardCollection;
import game.card.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import http.server.HttpStatus;
import http.server.Response;

import java.util.Arrays;
import java.util.List;

public class DeckController {

    public Response getDeck() throws JsonProcessingException {

        // Authentication Method - get Username in return

        // get id from User Deck
        // get Cards in the User Deck
        CardRepository cardRepository = new CardRepository();
        CardCollection cardCollection = cardRepository.getPackageFromCollection();

        //Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(cardCollection.getCards());

        Response response = new Response(HttpStatus.OK, jsonString);

        return response;
    }

    public Response configurateDeck(String deckData) {
        Response response;

        // Convert body to List
        deckData = deckData.replace("[", "").replace("]", "");
        String[] data = deckData.split(",");
        List<String> deckDataList = Arrays.asList(data);

        if(deckDataList.size() == 4) {
            // Postgres check if all Cards are owned by User
            // create new Deck
            // add DeckId to cards
            response = new Response(HttpStatus.OK);
        } else {
            response = new Response(HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
