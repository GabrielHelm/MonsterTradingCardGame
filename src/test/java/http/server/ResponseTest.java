package http.server;

import game.ParsingClasses.UserProfile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ResponseTest {
    private Response response;

    @Test
    void testParseSimpleResponse() {
        // Arrange
        response = new Response(HttpStatus.OK, "Successful request");
        String expectedResponseString = "HTTP/1.1 200 OK\n" +
                "Content-Length: 19\n" +
                "Content-Type: text/plain\n" +
                "\n" +
                "Successful request\n";

        // Act
        String responseStringToSend = response.toString();
        // Assert
        assertEquals(HttpStatus.OK, response.getHttpStatus());
        assertEquals("Successful request", response.getBody());
        assertEquals(19, response.getHeaders().get("Content-Length"));
        assertEquals("text/plain", response.getHeaders().get("Content-Type"));
        assertEquals(expectedResponseString, responseStringToSend);
    }

    @Test
    void testParseEmptyStringResponse() {
        // Arrange
        response = new Response(HttpStatus.CREATED, "");
        String expectedResponseString = "HTTP/1.1 201 Created\n" +
                "Content-Length: 0\n\n";

        // Act
        String responseStringToSend = response.toString();
        // Assert
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());
        assertEquals("", response.getBody());
        assertEquals(0, response.getHeaders().get("Content-Length"));
        assertEquals(expectedResponseString, responseStringToSend);
    }

    @Test
    void testParseEmptyBodyResponse() {
        // Arrange
        response = new Response(HttpStatus.CREATED);
        String expectedResponseString = "HTTP/1.1 201 Created\n" +
                "Content-Length: 0\n\n";

        // Act
        String responseStringToSend = response.toString();
        // Assert
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());
        assertNull(response.getBody());
        assertEquals(0, response.getHeaders().get("Content-Length"));
        assertEquals(expectedResponseString, responseStringToSend);
    }


    @Test
    void testParseJSONResponse() {
        // Arrange
        UserProfile userProfile = new UserProfile("Gabriel", "playing MTCG", ":-)");
        response = new Response(HttpStatus.OK, userProfile);
        String expectedResponseString = "HTTP/1.1 200 OK\n" +
                "Content-Length: 54\n" +
                "Content-Type: application/json\n" +
                "\n" +
                "{\"name\":\"Gabriel\",\"bio\":\"playing MTCG\",\"image\":\":-)\"}\n";

        // Act
        String responseStringToSend = response.toString();
        // Assert
        assertEquals(HttpStatus.OK, response.getHttpStatus());
        assertEquals("{\"name\":\"Gabriel\",\"bio\":\"playing MTCG\",\"image\":\":-)\"}", response.getBody());
        assertEquals(54, response.getHeaders().get("Content-Length"));
        assertEquals("application/json", response.getHeaders().get("Content-Type"));
        assertEquals(expectedResponseString, responseStringToSend);
    }

    @Test
    void testParseJSONResponseWithNullObject() {
        // Arrange
        UserProfile userProfile = null;
        response = new Response(HttpStatus.OK, userProfile);
        String expectedResponseString = "HTTP/1.1 200 OK\n" +
                "Content-Length: 0\n\n";

        // Act
        String responseStringToSend = response.toString();
        // Assert
        assertEquals(HttpStatus.OK, response.getHttpStatus());
        assertNull(response.getBody());
        assertEquals(0, response.getHeaders().get("Content-Length"));
        assertEquals(expectedResponseString, responseStringToSend);
    }
}