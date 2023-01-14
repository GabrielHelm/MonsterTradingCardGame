package game.ParsingClasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import http.server.SecurityHelper;

public class UserProfile {

    private String name;
    private String bio;
    private String image;

    public UserProfile(){
        name = "";
        bio = "";
        image = "";
    }
    @JsonCreator
    public UserProfile(@JsonProperty("Name")String name, @JsonProperty("Bio")String bio, @JsonProperty("Image")String image) {
        this.name = name;
        this.bio = bio;
        this.image = image;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
