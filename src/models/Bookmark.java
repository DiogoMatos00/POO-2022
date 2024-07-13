package models;

import java.io.Serializable;

public interface Bookmark extends retrieveName {
    public String getUrl();
    public bookmarkType getType();
    public String getUserID();
}
