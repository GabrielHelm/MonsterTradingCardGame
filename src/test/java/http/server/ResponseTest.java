package http.server;

import game.UserProfile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseTest {
    private Response response;

    @Test
    void testParseSimpleResponse() {
        // Arrange
        response = new Response(HttpStatus.OK, "Successful request");
        String expectedResponseString = "HTTP/1.1 200 OK\n" +
                "Content-Length: 18\n" +
                "Content-Type: text/plain\n" +
                "\n" +
                "Successful request";

        // Act
        String responseStringToSend = response.toString();
        // Assert
        assertEquals(HttpStatus.OK, response.getHttpStatus());
        assertEquals("Successful request", response.getBody());
        assertEquals(18, response.getHeaders().get("Content-Length"));
        assertEquals("text/plain", response.getHeaders().get("Content-Type"));
        assertEquals(expectedResponseString, responseStringToSend);
    }

    @Test
    void testParseJSONResponse() {
        // Arrange
        UserProfile userProfile = new UserProfile("Gabriel", "playing MTCG", ":-)");
        response = new Response(HttpStatus.OK, userProfile);
        String expectedResponseString = "HTTP/1.1 200 OK\n" +
                "Content-Length: 53\n" +
                "Content-Type: application/json\n" +
                "\n" +
                "{\"name\":\"Gabriel\",\"bio\":\"playing MTCG\",\"image\":\":-)\"}";

        // Act
        String responseStringToSend = response.toString();
        // Assert
        assertEquals(HttpStatus.OK, response.getHttpStatus());
        assertEquals("{\"name\":\"Gabriel\",\"bio\":\"playing MTCG\",\"image\":\":-)\"}", response.getBody());
        assertEquals(53, response.getHeaders().get("Content-Length"));
        assertEquals("application/json", response.getHeaders().get("Content-Type"));
        assertEquals(expectedResponseString, responseStringToSend);
    }
}