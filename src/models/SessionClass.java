package models;
import java.io.Serializable;
import java.util.*;


public class SessionClass implements Session, Serializable {
    private String name;
    private Date dt_creation;
    private String owner;
    private ArrayList<SessionKey> Bookmark;

    public SessionClass(String name, Date dt_creation, String owner, ArrayList Bookmark){
        this.name = name;
        this.dt_creation = dt_creation;
        this.owner = owner;
        this.Bookmark = Bookmark;
    }

    public String getName(){
        return this.name;
    }

    public Date getDt_creation(){
        return this.dt_creation;
    }

    public String getOwner(){
        return this.owner;
    }

    public ArrayList getBookmarks(){
        return this.Bookmark;
    }

    public void addBookmark(SessionKey value){
        this.Bookmark.add(value);
    }

}
