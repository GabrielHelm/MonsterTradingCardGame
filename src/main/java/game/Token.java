package game;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Token {

    public Token(String name, Timestamp valid_until) {
        this.name = name;
        this.valid_until = valid_until;
    }

    private String name;
    private Timestamp valid_until;

    public Token(User user) {
        this.name = user.getCredentials().getUsername() + "-mtcgToken";
        this.valid_until = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));
    }

    public void updateToken() {
        valid_until = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));
    }

    public String getName() {
        return name;
    }

    public Timestamp getValid_until() {
        return valid_until;
    }

}
