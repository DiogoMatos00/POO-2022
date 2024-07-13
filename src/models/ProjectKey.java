package models;
import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

public class ProjectKey implements Serializable {
    private String BookmarkName;
    private String OwnerId;
    private String Id;
    private String Url;
    private List<String> path;
    private Date date;

    public ProjectKey(String BookmarkName, String OwnerId, String Id, String Url, List path, Date date){
        this.BookmarkName = BookmarkName;
        this.OwnerId = OwnerId;
        this.Id = Id;
        this.Url = Url;
        this.path = path;
        this.date = date;
    }

    public void delete(){
        this.Url = "deleted";
    }

    public String getBookmarkName() {
        return this.BookmarkName;
    }

    public String getUrl() {
        return this.Url;
    }

    public String getId() {
        return this.Id;
    }

    public String getOwnerId() {
        return this.OwnerId;
    }

    public List getPath() {
        return this.path;
    }

    public Date getDate(){
        return this.date;
    }

    public List<String> gettruepath(){
        return this.path;
    }


    public String getpath(){
        List path = this.path;
            String newpath = (String) path.stream()
                    .map(n -> String.valueOf(n))
                    .collect((Collectors.joining(" > ", "", " : ")));

            if(newpath.matches(" : ")) {
                String srow = this.BookmarkName + " " + this.OwnerId + " " + this.Id + " " + this.Url;
                return srow;
            } else {
                String srow = newpath + this.BookmarkName + " " + this.OwnerId + " " + this.Id + " " + this.Url;
                return srow;
            }
    }
}
