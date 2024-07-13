package models;
import java.io.Serializable;
import java.util.Map;
import models.BookmarkClass.*;

public class UserClass implements User, Serializable {
    private String name;
    private Integer id;
    private Map<String, BookmarkClass> Bookmarks;
    private Map<String, ProjectParticipation> UserProjects;
    private Map<String, BookmarkClass> UserShareCollection;

    public UserClass(String name, int id, Map Bookmarks, Map Userprojects, Map UserShareCollection){
        this.name = name;
        this.id = id;
        this.Bookmarks = Bookmarks;
        this.UserProjects = Userprojects;
        this.UserShareCollection = UserShareCollection;
    }

    public String getName(){
        return this.name;
    }

    public String getId(){
        String id = String.valueOf(this.id);
        return id;
    }

    public Map getBookmarks(){ return this.Bookmarks; }

    public Map getUserProjects(){ return this.UserProjects; }

    public Map getUserShareCollection(){
        return this.UserShareCollection;
    }

    public void addBookmarks(BookmarkClass newbk, String name){
        this.Bookmarks.put(name, newbk);
    }

    public void addSharedBookmarks(BookmarkClass newbk, String name){
        this.UserShareCollection.put(name, newbk);
    }

    public void addProject(String id, ProjectParticipation projectparticipation){
        this.UserProjects.put(id, projectparticipation);
    }
}