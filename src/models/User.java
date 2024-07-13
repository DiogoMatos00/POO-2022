package models;
import java.util.Map;

public interface User extends retrieveBookmarks, retrieveName {

    public String getId();
    public Map getUserProjects();
    public Map getUserShareCollection();
    public void addBookmarks(BookmarkClass newbk, String name);
    public void addSharedBookmarks(BookmarkClass newbk, String name);
    public void addProject(String id, ProjectParticipation value);

}
