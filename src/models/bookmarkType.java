package models;

import java.io.Serializable;

public class bookmarkType implements Serializable {
    String type;

    public bookmarkType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
