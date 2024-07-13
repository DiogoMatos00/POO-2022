package models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class ProjectClass implements Project, Serializable {
    private String name;
    private Date dt_creation;
    private Map<String, UserClass> Users;
    private Map<String, BookmarkClass> BookmarkFirstLayer;
    private Map<String, DirectoryClass> Directories;  //Ver e guardar objeto todo
    private ArrayList<ProjectKey> allbk;
    private String id;
    private String ownerID;

    public ProjectClass(String name,Date dt_creation, Map Users, Map Bookmarks, Map Directories, ArrayList allbk, String id, String ownerID, Map BookmarkFirstLayer){
        this.name = name;
        this.dt_creation = dt_creation;
        this.Users = Users;
        this.Directories = Directories;
        this.id = id;
        this.ownerID = ownerID;
        this.allbk = allbk;
        this.BookmarkFirstLayer = BookmarkFirstLayer;
    }

    public String getName(){
        return this.name;
    }

    public Date getDt_creation(){
        return this.dt_creation;
    }

    public Map getUsers(){
        return this.Users;
    }

    public void getBookmarks(){}

    public Map getDirectory(){
        return this.Directories;
    }

    public ArrayList<ProjectKey> getallbk(){
        return this.allbk;
    };

    public Map getBookmarkFirstLayer() {
        return this.BookmarkFirstLayer;
    }

    public String getId(){
        return this.id;
    }

    public String getOwner(){
        return this.ownerID;
    }

    public void addUser(String id, UserClass user){
        this.Users.put(id, user);
    }

    public void addBookmark(String key, addbktoproject value){
        //this.Bookmarks.put(key, value);
    }

    public void addallbk(ProjectKey value){
        this.allbk.add(value);
    }

    public void addDirectory(String key, DirectoryClass value){
        this.Directories.put(key, value);
    }


    public void addFirstBookmarkLayer(String key, BookmarkClass value){
        this.BookmarkFirstLayer.put(key, value);
    }



}

