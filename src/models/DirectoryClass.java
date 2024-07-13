package models;
import models.BookmarkClass.*;

import java.io.Serializable;
import java.util.*;

public class DirectoryClass implements Directory, Serializable {
    private Map<String, DirectoryClass> Directory;
    private Map<String, BookmarkClass> Bookmark;
    private String name;
    private List<String> path;

    public DirectoryClass(Map Directory, Map Bookmark, String name, List<String> path){
        this.Directory = Directory;
        this.Bookmark = Bookmark;
        this.name = name;
        this.path = path;
    }

    public Map getDirectory(){
        return this.Directory;
    }

    public boolean hasDirectory(String name){
        return this.Directory.containsKey(name);
    }

    public Map getBookmarks(){
        return this.Bookmark;
    }

    public String getName(){
        return this.name;
    }

    public List getPath(){
        return this.path;
    }

    public void addDir(String id, DirectoryClass value){
        this.Directory.put(id, value);
    }

    public void addBkToDir(String name, BookmarkClass value){
        this.Bookmark.put(name, value);
    }
}
