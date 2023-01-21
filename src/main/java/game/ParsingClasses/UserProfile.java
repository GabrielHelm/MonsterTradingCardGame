package game.ParsingClasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfile {

    private String name;
    private String bio;
    private String image;

    public UserProfile(String name){
        this.name = name;
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

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }

}
