package models;
import java.util.List;

public interface Directory extends retrieveBookmarks, retrieveDirectories, retrieveName {
    public List getPath();
    public void addDir(String id, DirectoryClass value);
    public void addBkToDir (String name, BookmarkClass value);
}
