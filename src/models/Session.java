package models;

import java.util.ArrayList;

public interface Session extends creationDetails, retrieveName{
    public void addBookmark(SessionKey value);

    public ArrayList getBookmarks();
}
