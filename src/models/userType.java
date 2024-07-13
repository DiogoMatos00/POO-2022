package models;

import java.io.Serializable;

public class userType implements Serializable {
    private String type;

    public userType(String type){
        this.type = type;
    }

    public String gettype(){
        return this.type;
    }
}
