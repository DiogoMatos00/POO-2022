package models;

import java.io.Serializable;

public class SessionKey implements Serializable {
    private String name;
    private String ownerId;
    private BookmarkClass bookmark;

    public SessionKey(BookmarkClass bookmark, String name, String ownerId){
        this.bookmark = bookmark;
        this.name = name;
        this.ownerId = ownerId;
    }

    public BookmarkClass getBookmark(){
        return this.bookmark;
    }

    public String getName(){
        return this.name;
    }

    public String getOwnerId(){
        return this.ownerId;
    }
}
