package models;
import java.io.Serializable;
import java.util.Date;


public class BookmarkClass implements Bookmark, Serializable {
    private String name;
    private String url;
    private Date dt_creation;
    private bookmarkType type;
    private String userID;

    public BookmarkClass(String name, String url, Date dt_creation, bookmarkType type, String userID){
        this.name = name;
        this.url = url;
        this.dt_creation = dt_creation;
        this.type = type;
        this.userID = userID;
    }
    
    public String getName(){
        return this.name;
    }

    public String getUrl(){
        return this.url;
    }

    public Date getDt_creation(){
        return this.dt_creation;
    }

    public bookmarkType getType(){
        return this.type;
    }

    public String getUserID(){
        return this.userID;
    }

}
