package game.ParsingClasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import http.server.SecurityHelper;

public class Credentials {

    private String username;

    private String password;

    public Credentials() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonCreator
    public Credentials(@JsonProperty("Username") String username, @JsonProperty("Password") String password ){
        this.username = username;
        this.password = new SecurityHelper().hashWith256(password);
    }
}
