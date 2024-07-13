package models;
import models.BookmarkClass;

import java.awt.print.Book;
import java.io.Serializable;
import java.util.List;


public class addbktoproject implements Serializable {
    private String bkcreatorid;
    private String userid;
    private BookmarkClass bookmark;
    private List<String> path;

    public addbktoproject(String bkcreatorid, String userid, BookmarkClass bookmark, List<String> path){
        this.bkcreatorid = bkcreatorid;
        this.userid = userid;
        this.bookmark = bookmark;
        this.path = path;
    }

    public String getBkcreatorid(){
        return this.bkcreatorid;
    }

    public String getUserid(){
        return this.userid;
    }

    public BookmarkClass getBookmark(){
        return this.bookmark;
    }

    public List<String> getPath(){
        return this.path;
    }

    public String createKey(){
        String name = this.bookmark.getName();
        return name+"_"+this.bkcreatorid+"_"+this.userid;
    }


}