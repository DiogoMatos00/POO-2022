package controller;

import java.awt.print.Book;
import java.io.*;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

import models.*;

public class BookmarkManager implements Serializable{
    private Map<String, UserClass> users = new HashMap<>();
    private Map<String, ProjectClass> projects = new HashMap<>();
    private Map<String, SessionClass> sessions = new HashMap<>();
    private int id = 0; //User Id
    private Integer projectid = 0; // Project Id

    public UserClass getUser(String key){
        return users.get(key);
    }

    public ProjectClass getProject(String key){
        return projects.get(key);
    }

    public SessionClass getSession(String key) {
        return sessions.get(key);
    }

    public int registerUser(final String name){
        Map<String, BookmarkClass> Personalbookmarks = new HashMap<>();
        Map<String, ProjectClass> ProjectParticipation = new HashMap<>();
        Map<String, BookmarkClass> Sharedbookmarks = new HashMap<>();
        id++;
        final var user = new UserClass(name, id, Personalbookmarks, ProjectParticipation, Sharedbookmarks);
        String key = user.getId(); //Get User Id for a key
        users.put(key, user); //add to bkmanager "users" map
        return id; // return for output
    }

    public boolean hasUser(String id) {
        if (this.existUser() == true) {
            return false;
        } else {
            return users.containsKey(id);
        }
    }

    public boolean existUser(){
        return users.isEmpty();
    }

    public List listUser(){
        List<List> UserList = new ArrayList<>(); //This is a list of lists and each list will have an user.

        for(Integer i=0; i<id; i++){
            Integer nchave = i + 1;
            String chave = nchave.toString();
            UserClass user = getUser(chave);
            String userid = user.getId();
            String username = user.getName();

            List<String> atributes = new ArrayList<>();

            atributes.add(userid);
            atributes.add(username);
            UserList.add(atributes);
        }

        return UserList;
    }

    public boolean hasBookmark(String name, String id){
        UserClass user = getUser(id);
        Map<String, BookmarkClass> bkCollection = user.getBookmarks();

        if(bkCollection.get(name) == null){
            return false;
        } else{
            return true;
        }
    }

    public boolean isDateValid(String date){

        ArrayList a = new ArrayList<>(Arrays.asList(date.split("")));
        if(a.size() == 8) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

            String dateInString = date;

            Date truedate = null;
            try {
                truedate = formatter.parse(dateInString);
            } catch (ParseException e) {
                return false;
            }
            return true;
        } else{
            return false;
        }
    }

    public boolean isTypeValid(String type) {  //É para todos
        if (type.matches("Outro")  || type.matches("Pessoal") || type.matches("Trabalho")) {
            return true;
        } else{
            return false;
        }
    }

    public void registerBookmark(String name, String url, String date, String type, String idUser){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

        String dateInString = date;

        Date truedate = null;
        try {
            truedate = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        bookmarkType newtype = new bookmarkType(type);

        final var bookmark = new BookmarkClass(name, url, truedate, newtype, idUser);

        UserClass user = getUser(idUser);
        user.addBookmarks(bookmark, name);
    }

    public boolean hasPermission(String type){
        if(type.matches("Trabalho") || type.matches("Outro")){
            return true;
        }else{
            return false;
        }
    }

    public boolean bookMarkShare(String owner, String id, String name){
        UserClass userOwner = getUser(owner);
        Map bk = userOwner.getBookmarks();
        BookmarkClass bktoshare = (BookmarkClass) bk.get(name);

        String type = bktoshare.getType().getType();
        if(hasPermission(type) == false){
            return false;
        }

        UserClass userShare = getUser(id);
        userShare.addSharedBookmarks(bktoshare, name);
        return true;
    }

    public String createProject(String date, String name, String ownerid){
        Map<String, UserClass> usersInProject = new HashMap<>();
        Map<String, BookmarkClass> bookmarksInProject = new HashMap<>();
        Map<String, DirectoryClass> directoriesInProject = new HashMap<>();
        ArrayList<ProjectKey> allbk = new ArrayList<>();
        projectid++;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

        String dateInString = date;

        Date dateproject = null;
        try {
            dateproject = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String projectids = projectid.toString();

        final var project = new ProjectClass(name, dateproject,usersInProject, bookmarksInProject, directoriesInProject, allbk, projectids, ownerid, bookmarksInProject);
        projects.put(projectids, project);

        UserClass user = this.getUser(ownerid);

        userType usertype = new userType("Owner");

        var projectparticipation = new ProjectParticipation(usertype, user, project);
        user.addProject(projectids, projectparticipation);

        project.addUser(ownerid, user);

        return projectids;
    }

    public boolean hasProject(String projectId){
        return projects.containsKey(projectId);
    }

    public void addUserToProject(String ownerId, String projectId, String id, String type){
        UserClass user = this.getUser(id);
        ProjectClass project = this.getProject(projectId);

        userType usertype = new userType(type);

        var projectparticipation = new ProjectParticipation(usertype, user, project);

        user.addProject(projectId, projectparticipation);
        project.addUser(id, user);
    }

    public List<String> checkProject(String ownerId, String projectId){
        List<ProjectKey> projectscheck = new ArrayList<>();
        List<String> rows = new ArrayList<>();

        ProjectClass a = projects.get(projectId);

        ArrayList allbk = a.getallbk();

        for(int i=0;i<allbk.size();i++){
            rows = new ArrayList<>();

            ProjectKey b = (ProjectKey) allbk.get(i);

            projectscheck.add(b);
        }


        projectscheck.sort(new Comparator<ProjectKey>() {
            @Override
            public int compare(ProjectKey o1, ProjectKey o2) {
                return o1.getpath().compareTo(o2.getpath()) + o1.getDate().compareTo(o2.getDate());
            }
        });

        List<String> finalprojectscheck = new ArrayList<>();

        for(int i = 0; i<projectscheck.size();i++){
            ProjectKey b = projectscheck.get(i);
            String c = b.getpath();
            finalprojectscheck.add(c);
        }

        finalprojectscheck.add(0, a.getName());

        return finalprojectscheck;
    }

    public boolean isUserTypeValid(String type){
        if(type == "Membro" || type == "Gestor"){
            return true;
        } else{
            return false;
        }
    }

    public boolean doesBookmarkAllowOperation(String type){
        if(type.matches("Pessoal") || type.matches("Trabalho")){
            return true;
        } else{
            return false;
        }
    }

    public boolean isBookmarkInProject(String name, String id, ArrayList getbk){
        ProjectClass project = getProject(id);
        getbk = project.getallbk();
        if(getbk.contains(name)){
            return true;
        }else{
            return false;
        }
    }

    public boolean addBookmarkToProject(String ownerId, String projectId, String userid, String owner, String nameBK, String name1){
        ProjectClass project = getProject(projectId);
        UserClass user = getUser(owner);
        Map bookmarklist = user.getBookmarks();

        BookmarkClass bookmark = (BookmarkClass) bookmarklist.get(nameBK);

        if(this.hasPermission(bookmark.getType().getType()) == false){
            return false;
        }




        Map dir = project.getDirectory();



        var nameDir = new ArrayList<>(Arrays.asList(name1.split(" > ")));

        String name = bookmark.getName();
        String key1 = name + "_" + owner + "_" + userid;

        DirectoryClass dir1 = null;

        ProjectKey addbk = new ProjectKey(bookmark.getName(), bookmark.getUserID(), userid, bookmark.getUrl(), nameDir, bookmark.getDt_creation());

        if(nameDir.get(0) == ""){
            project.addFirstBookmarkLayer(bookmark.getName(), bookmark);
        } else{
            for (int i=0; i<nameDir.size(); i++){
                String key = nameDir.get(i);
                dir1 = (DirectoryClass) dir.get(key);
                Map sim = dir1.getDirectory();
                dir = sim;
            }

            Map bk = dir1.getBookmarks();
            bk.put(key1, bookmark);
        }


        project.addallbk(addbk);
        return true;
    }

    public void removeBookmarkFromProject(String ownerId, String projectId, String user, String owner, String name){
        ProjectClass project = getProject(projectId);
        ArrayList allbk = project.getallbk();
        Map bk = project.getBookmarkFirstLayer();


        Map ola = project.getDirectory();
        DirectoryClass dir1 = null;
        ProjectKey a = null;

        List bkpath = null;

        ProjectKey b = (ProjectKey) allbk.get(0);

        String comppath = b.gettruepath().get(0);

        Map listbk = null;

        for (int i = 0; i < allbk.size(); i++) {
            ProjectKey ebk = (ProjectKey) allbk.get(i);
            if (ebk.getBookmarkName().matches(name)) {
                bkpath = ebk.gettruepath();
                a = (ProjectKey) allbk.remove(i);
                break;
            }
        }


        if(comppath.matches("")){
            listbk = project.getBookmarkFirstLayer();
        } else {

            for (int i = 0; i < bkpath.size() - 1; i++) {
                String key = (String) bkpath.get(i);

                dir1 = (DirectoryClass) ola.get(key);
                Map sim = dir1.getDirectory();

                ola = sim;
            }
            listbk = dir1.getBookmarks();
        }

        listbk.remove(name + "_" + owner + "_" + user);
    }

    public boolean userAuthorized(String id, String projectid){
        ProjectClass project = projects.get(projectid);
        //Map users = project.getUsers();
        Map a = this.getUser(id).getUserProjects();
        //ProjectParticipation user = (ProjectParticipation) users.get(id);
        ProjectParticipation user = (ProjectParticipation) a.get(projectid);;

        userType typetype = user.getType();
        String type = typetype.gettype();

        if(type.matches("Gestor") || type.matches("Owner")){
            return true;
        }else{
            return false;
        }
    }

    public boolean hasDirectory(String name, String projectid){
        ProjectClass project = getProject(projectid);
        Map directory = project.getDirectory();
        var nameDir = new ArrayList<>(Arrays.asList(name.split(" > ")));

        int f = 0;

        //if(directory.isEmpty()){
        //    return false;
        //}

        //System.out.println(name);
        //System.out.println(directory.get(name.get(0)));
        if(nameDir.size() == 2){
            DirectoryClass a = (DirectoryClass) directory.get(nameDir.get(0));
            a.getDirectory();
            //System.out.println("A: " + a.getDirectory().get(name.get(1)));
        }

        for(int i = 0;i<nameDir.size();i++){
            if(directory.containsKey(nameDir.get(i)) == true){
                DirectoryClass a = (DirectoryClass) directory.get(nameDir.get(i));
                directory = a.getDirectory();
            } else{
                return false;
            }
        }
        return true;
    }

    public boolean checkHierarchy(String name, String projectid) {
        ProjectClass project = getProject(projectid);
        Map currentDir = project.getDirectory();
        var nameDir = new ArrayList<>(Arrays.asList(name.split(" > ")));

        if(nameDir.size() > 1) {
            try {
                for (int i = 0; i < nameDir.size() - 1; i++) {
                    String key = nameDir.get(i);
                    DirectoryClass a = (DirectoryClass) currentDir.get(key);
                    Map sim = a.getDirectory();
                    currentDir = sim;
                }
            } catch (Exception e){
                return false;
            }

        }


        if (nameDir.size() == 1) { //DirA > DirB > DirC = [DirA, DirB...
            //if(!currentDir.containsKey(nameDir.get(0))){
            //    return false;
            //}
            if(project.getDirectory().isEmpty()){
                return true;
            }

        } else {
            //nameDir.remove(nameDir.size() - 1);
            currentDir = project.getDirectory();
            for (int i = 0; i < nameDir.size() - 1; i++) {
                String key = nameDir.get(i);
                //if(project.getDirectory().isEmpty()){
                //    return false;
                //}



                DirectoryClass dir1 = (DirectoryClass) currentDir.get(key);
                if(dir1.getDirectory().isEmpty()){
                    return true;
                }

                if(!dir1.hasDirectory(nameDir.get(i+1))){
                    return false;
                }
                currentDir = dir1.getDirectory();
            }

        }
        return true;
    }

    public boolean hasbkforproject(String name, String Ownerid, String id){
        UserClass user = getUser(Ownerid);
        Map<String, BookmarkClass> bkCollection = user.getBookmarks();
        Map bkshared = user.getUserShareCollection();

        if(!Ownerid.equals(id)){
            bkCollection = bkshared;
        }
        if(bkCollection.get(name) == null){
            return false;
        } else{
            return true;
        }
    }

    public boolean addDirectoryToProject(String ownerId, String projectId, String user, String name1) {
        ProjectClass project = getProject(projectId);
        Map<String, DirectoryClass> directories = new HashMap<>();
        Map<String, BookmarkClass> bookmarks = new HashMap<>();

        UserClass userc = users.get(user);
        ProjectParticipation type = (ProjectParticipation) userc.getUserProjects().get(projectId);


        if(type.getType().gettype().matches("Membro")){
            return false;
        }


        Map myDir = project.getDirectory();
        var nameDir = new ArrayList<>(Arrays.asList(name1.split(" > ")));


        //nameDir.removeAll(Collections.singleton(" > "));
        int paths = nameDir.size();
        String name = nameDir.remove(nameDir.size()-1);

        DirectoryClass directory = new DirectoryClass(directories, bookmarks, name, nameDir);

        if(paths > 1) {
            for (int i = 0; i < paths - 1; i++) {
                String key = nameDir.get(i);
                DirectoryClass a = (DirectoryClass) myDir.get(key);
                Map sim = a.getDirectory();
                myDir = sim;
            }
            myDir.put(name, directory);
        } else {
            myDir.put(name, directory);
        }
        return true;
    }

    public boolean removeDirectoryFromProject(String ownerId, String projectId, String user, String name){
        ProjectClass project = getProject(projectId);

        var nameDir = new ArrayList<>(Arrays.asList(name.split(" > ")));

        boolean loop = true;
        ArrayList<ProjectKey> allbk = project.getallbk();

        UserClass userc = users.get(user);
        ProjectParticipation type = (ProjectParticipation) userc.getUserProjects().get(projectId);

        if(type.getType().gettype().matches("Membro")){
            return false;
        }

        for(int j = 0; j<allbk.size(); j++) {
            ProjectKey pk = allbk.get(j);
            List<String> path = pk.gettruepath();

            for (int i = 0; i < nameDir.size(); i++) {
                if(path.get(i) == nameDir.get(i)){
                    continue;
                } else{
                    loop = false;
                    break;
                }
            }

            if(loop == true){
                ProjectKey a = allbk.get(j);
                a.delete();
            } else {
                loop = true;
            }

        }

        Predicate<ProjectKey> condition = ProjectKey -> ProjectKey.getUrl() == "deleted";
        allbk.removeIf(condition);


        DirectoryClass dir1 = null;
        Map ola = project.getDirectory();

        for(int i = 0; i<nameDir.size() - 1; i++) {
                String key = nameDir.get(i);
                dir1 = (DirectoryClass) ola.get(key);
                Map sim = dir1.getDirectory();

                ola = sim;

            }

        ola.remove(nameDir.get(nameDir.size() - 1));

        //Map directories = (Map) dir1.getDirectory();
        //directories.remove(nameDir.get(nameDir.size() - 1));
        return true;
    }

    public boolean hasSession(String id, String name){
        String key = name + id;
        return sessions.containsKey(key);
    }

    public ArrayList checkSession(String ownerId, String name){
        SessionClass session = this.getSession(name + ownerId);
        ArrayList bookmark = session.getBookmarks();
        ArrayList rows;
        ArrayList sessioncheck = new ArrayList();

        //sessioncheck.add(session.getDt_creation());

        for(int i = 0; i<bookmark.size(); i++){
            SessionKey bk = (SessionKey) bookmark.get(i);
            sessioncheck.add(bk);
        }

        sessioncheck.sort(new Comparator<SessionKey>() {
            @Override
            public int compare(SessionKey o1, SessionKey o2) {
                String a1 = o1.getBookmark().getUserID();
                String a2 = o2.getBookmark().getUserID();

                return o1.getName().compareTo(o2.getName()) + a1.compareTo(a2);
            }
        });

        ArrayList finalsessioncheck = new ArrayList();

        for(int i = 0; i<sessioncheck.size(); i++){
            SessionKey bk = (SessionKey) sessioncheck.get(i);

            rows = new ArrayList<>();

            rows.add(bk.getName());
            rows.add(bk.getBookmark().getUrl());


            finalsessioncheck.add(rows);
        }

        finalsessioncheck.add(0, session.getDt_creation());

        return finalsessioncheck;
    }

    public void createSession(String id, String date, String name){
        ArrayList<SessionKey> bookmarks = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

        String dateInString = date;

        Date truedate = null;
        try {
            truedate = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        final var session = new SessionClass(name, truedate, id, bookmarks);
        String chave = session.getName() + session.getOwner();
        sessions.put(chave, session);
    }

    public boolean addBookmarkToSession(String ownerId, String name, String owner, String bkname){
        SessionClass session = sessions.get(name + ownerId);
        UserClass user = getUser(owner);
        Map bookmarklist = user.getBookmarks();
        BookmarkClass bookmark = (BookmarkClass) bookmarklist.get(bkname);

        if(this.doesBookmarkAllowOperation(bookmark.getType().getType()) == false){
            return false;
        }





        String key1 = name + "_" + owner + "_" + ownerId;

        SessionKey sessionk = new SessionKey(bookmark ,bookmark.getName(), bookmark.getUserID());

        session.addBookmark(sessionk);

        return true;
    }

    public boolean isBookmarkInSession(String name, String bkowner, String nameSession, String ownerId){
        SessionClass session = this.getSession(nameSession + ownerId);
        ArrayList bookmark = session.getBookmarks();

        for(int i = 0; i<bookmark.size(); i++){
            SessionKey bk = (SessionKey) bookmark.get(i);
            if(bk.getName() == name && bk.getOwnerId() == bkowner){
                return true;
            }
        }
        return false;
    }

    public void removeBookmarkFromSession(String ownerId, String nameSession, String bkowner, String name){
        SessionClass session = this.getSession(nameSession + ownerId);
        ArrayList bookmark = session.getBookmarks();

        for(int i = 0; i<bookmark.size(); i++){
            SessionKey bk = (SessionKey) bookmark.get(i);
            if(bk.getName().matches(name) && bk.getOwnerId().matches(bkowner)){
                bookmark.remove(i);
                break;
            }
        }
    }

    public static BookmarkManager load(String filename) throws FileNotFoundException {
        FileInputStream fileInputStream = null;
        fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch(IOException e) {
            e.printStackTrace();
        }
        Object obj = null;
        try {
            obj = objectInputStream.readObject();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (BookmarkManager) obj;
    }

    public void save(String filename, BookmarkManager obj) {
        try {
            final var fileOutputStream = new FileOutputStream(filename);
            final var objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
        } catch(FileNotFoundException fnfe) {
            System.out.println("Ficheiro inexistente.");
        } catch(IOException ioe) {
            System.out.println("Erro na gravação de objetos.");
        }
    }
}
