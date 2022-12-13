package http;

import java.util.List;

public class Authentification {
    public boolean authenticate(List<Header> headers){

        String token = null;

        for (Header header : headers) {
            if (header.getName().equals("Authorization")) {
                token = header.getValue();
            }
        }
        if(token == null) {
            return false;
        }

        // Postgres Aufruf
        // check if token exists in Database and is valid
        // valid & exists
        return true;
        // invalid || does not exists
        // return false

    }
}
