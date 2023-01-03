package http.controller;

import game.card.Card;
import game.card.CardCollection;
import game.card.CardRepository;
import game.card.MonsterCard;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import http.server.HttpStatus;
import http.server.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PackageController {
    public Response createPackage(String packageData) throws JsonProcessingException {

        String uniqueID = UUID.randomUUID().toString();
        try {
            ObjectMapper mapper = new ObjectMapper();

            List<Card> cards = Arrays.asList(mapper.readValue(packageData, MonsterCard[].class));

            CardCollection cardCollection = new CardCollection();
            cardCollection.setU_ID(uniqueID);
            cardCollection.setCards(cards);
            cardCollection.printCollection();
            System.out.println("UID = " + cardCollection.getU_ID());

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Postgres Aufruf

        Response response = new Response(HttpStatus.CREATED);
        return response;
    }

    public Response getPackage() throws JsonProcessingException {
        // Authentication Method - get Username in return

        // check if User has enough coins

        Response response;
        // Postgres get random PackageID

        // get cards from Package
        CardRepository cardRepository = new CardRepository();
        CardCollection cardCollection = cardRepository.getPackageFromCollection();

        // change owner of acquired cards

        //Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(cardCollection.getCards());

        response = new Response(HttpStatus.OK, jsonString);
        // if no Package available or not enough coins
        //response = new Response(HttpStatus.BAD_REQUEST);

        return response;
    }
}
