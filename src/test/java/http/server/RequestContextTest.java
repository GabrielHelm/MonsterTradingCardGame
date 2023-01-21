package http.server;

import game.ParsingClasses.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestContextTest {
    private RequestContext requestContext;

    @BeforeEach
    void init() {
        requestContext = new RequestContext();
    }

    @Test
    void testSetPathWithParameter() {

        requestContext.setPath("/deck?format=plain");

        assertEquals("/deck", requestContext.getPath());
        assertEquals("format=plain", requestContext.getSubpath());
    }

    @Test
    void testSetPathWithCustomUserSubPath() {

        requestContext.setPath("/users/kienboec");

        assertEquals("/users/", requestContext.getPath());
        assertEquals("kienboec", requestContext.getSubpath());
    }

    @Test
    void testSetPathWithCustomTradingSubPath() {

        requestContext.setPath("/tradings/6cd85277-4590-49d4-b0cf-ba0a921faad0");

        assertEquals("/tradings/", requestContext.getPath());
        assertEquals("6cd85277-4590-49d4-b0cf-ba0a921faad0", requestContext.getSubpath());
    }

    @Test
    void testSimpleBodyObjectMapping() {

        requestContext.setBody("{\"Name\": \"Kienboeck\",  \"Bio\": \"me playin...\", \"Image\": \":-)\"}");

        UserProfile userProfile = requestContext.getBodyAs(UserProfile.class);

        assertEquals("Kienboeck", userProfile.getName());
        assertEquals("me playin...", userProfile.getBio());
        assertEquals(":-)", userProfile.getImage());
    }
}