package models;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Map;

public interface
Project extends retrieveName,retrieveDirectories, creationDetails {
    public String getId();
    public Map getUsers();
    public void addUser(String id, UserClass user);
    public void addDirectory(String key, DirectoryClass value);
    public ArrayList getallbk();
    public Map getBookmarkFirstLayer();
    public void addFirstBookmarkLayer(String key, BookmarkClass value);
    public void addallbk(ProjectKey value);

}
