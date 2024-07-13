package view;

//import java.awt.print.Book;
//import java.text.SimpleDateFormat;
//import java.util.Locale;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import controller.BookmarkManager;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.ArrayList;
//import java.util.Date;


public class Cli{
    public Cli(){
        final var scanner = new Scanner(System.in);
        BookmarkManager bookmarkmanager = new BookmarkManager();
        while(true) {
            final var line = scanner.nextLine();
            if(line.isBlank()){
                break;
            }
            var commands = new ArrayList<>(Arrays.asList(line.split(" ")));
            switch (commands.get(0)) {
                case "UR":  //Command "Registar utilizador"
                    if(commands.size() == 2){
                        var UserName = commands.get(1);
                        var Id = bookmarkmanager.registerUser(UserName);
                        System.out.println("Utilizador registado com identificador " + Id + ".");
                    }else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "UL": //Command "Listar utilizadores"
                    if(commands.size() == 1) {
                        if (bookmarkmanager.existUser() == true) {
                            System.out.println("Sem utilizadores registados.");
                        }else {
                            List<List> list = bookmarkmanager.listUser();
                            for (int i = 0; i < list.size(); i++) {
                                List<String> row = (List<String>) list.get(i);
                                String userid = row.get(0);
                                String username = row.get(1);
                                String output = MessageFormat.format("{0} {1}", userid, username);
                                System.out.println(output);
                            }
                        }
                    }else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "BR": //Commands Registar bookmark
                    if(commands.size() == 4) {
                        var UserId = commands.get(1);
                        var Date = commands.get(2);
                        var BkType = commands.get(3);
                        var BkName = scanner.nextLine();
                        var URL = scanner.nextLine();

                        if (bookmarkmanager.hasUser(UserId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasBookmark(BkName, UserId) == true) {
                            System.out.println("Bookmark existente.");
                        } else if (bookmarkmanager.isDateValid(Date) == false) {
                            System.out.println("Data inválida.");
                        } else if (bookmarkmanager.isTypeValid(BkType) == false) {
                            System.out.println("Tipo inválido.");
                        }  else {
                            bookmarkmanager.registerBookmark(BkName, URL, Date, BkType, UserId);
                            System.out.println("Bookmark registado com sucesso.");
                        }
                    } else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "BP": // Command Partilhar bookmark
                    if(commands.size() == 3) {
                        var UserId = commands.get(1);
                        var UserIdShare = commands.get(2);
                        var BkName = scanner.nextLine();

                        if (bookmarkmanager.hasUser(UserId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasUser(UserIdShare) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasBookmark(BkName, UserId) == false) {
                            System.out.println("Bookmark inexistente.");
                        } else {
                            if (bookmarkmanager.bookMarkShare(UserId, UserIdShare, BkName) == true) {
                                System.out.println("Bookmark partilhado com sucesso.");
                            } else {
                                System.out.println("Sem permissões.");
                            }
                        }
                    } else {
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "PR": //Command Criar projeto
                    if(commands.size() == 3) {
                        var UserId = commands.get(1);
                        var Date = commands.get(2);
                        var ProjectName = scanner.nextLine();

                        if (bookmarkmanager.hasUser(UserId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.isDateValid(Date) == false) {
                            System.out.println("Data inválida.");
                        } else {
                            String Projectid = bookmarkmanager.createProject(Date, ProjectName, UserId);
                            System.out.println("Projeto registado com identificador " + Projectid + ".");
                        }
                    } else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "PAP": //Commands Adicionar participante a projeto
                    if(commands.size() == 5) {
                        var UserId = commands.get(1);
                        var ProjectId = commands.get(2);
                        var UserIdShare = commands.get(3);
                        var UserType = commands.get(4);

                        if (bookmarkmanager.hasUser(UserId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasUser(UserIdShare) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasProject(ProjectId) == false) {
                            System.out.println("Projeto inexistente.");
                        } else {
                            bookmarkmanager.addUserToProject(UserId, ProjectId, UserIdShare, UserType);
                            System.out.println("Utilizador adicionado a projeto com sucesso.");
                        }
                    } else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "PC":
                    if(commands.size() == 3) {
                        var UserId = commands.get(1);
                        var ProjectId = commands.get(2);
                        if (bookmarkmanager.hasUser(UserId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasProject(ProjectId) == false) {
                            System.out.println("Projeto inexistente.");
                        } else {
                            List<String> a = (List<String>) bookmarkmanager.checkProject(UserId, ProjectId);
                            System.out.println();
                            for(int i = 0; i<a.size();i++){
                                System.out.println(a.get(i));
                            }
                        }
                    }else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "PAB":
                    if(commands.size() == 4) {
                        var UserPCId = commands.get(1);
                        var ProjectId = commands.get(2);
                        var UserId = commands.get(3);

                        var a = scanner.nextLine();
                        var IdandName = new ArrayList<>(Arrays.asList(a.split(" ")));
                        var UserIdShare = IdandName.get(0);
                        IdandName.remove(0);
                        var BkName = String.join(" ", IdandName);

                        var path = scanner.nextLine();


                        if (bookmarkmanager.hasUser(UserPCId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasUser(UserIdShare) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasUser(UserId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasProject(ProjectId) == false) {
                            System.out.println("Projeto inexistente.");
                        } else if (bookmarkmanager.hasbkforproject(BkName, UserId, UserIdShare) == false) {
                            System.out.println("Bookmark inexistente.");
                        } else {
                            if(bookmarkmanager.addBookmarkToProject(UserPCId, ProjectId, UserId, UserIdShare, BkName, path) == true) {
                                System.out.println("Bookmark associado a projeto com sucesso.");
                            } else {
                                System.out.println("Sem permissões.");
                            }
                        }
                    }else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "PRB":
                    if(commands.size() == 4) {
                        var UserPCId = commands.get(1); // User Project Creator
                        var ProjectId = commands.get(2);
                        var UserIdShare = commands.get(3);


                        var a = scanner.nextLine();
                        var IdandName = new ArrayList<>(Arrays.asList(a.split(" ")));
                        var UserId = IdandName.get(0);
                        IdandName.remove(0);
                        var BkName = String.join(" ", IdandName);

                        if (bookmarkmanager.hasUser(UserPCId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasUser(UserIdShare) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasUser(UserId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasProject(ProjectId) == false) {
                            System.out.println("Projeto inexistente.");
                        } else if (bookmarkmanager.hasBookmark(BkName, UserId) == false) {
                            System.out.println("Bookmark inexistente no projeto.");
                        } else {
                            bookmarkmanager.removeBookmarkFromProject(UserPCId, ProjectId, UserIdShare, UserId, BkName);
                            System.out.println("Bookmark removido de projeto com sucesso.");
                        }
                    } else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "PAD":
                    if(commands.size() == 4) {
                        var UserPCId = commands.get(1); // User Project Creator
                        var ProjectId = commands.get(2);
                        var UserId = commands.get(3);

                        var NameDir = scanner.nextLine();

                        if (bookmarkmanager.hasUser(UserPCId) == false) {
                            System.out.print("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasUser(UserId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasProject(ProjectId) == false) {
                            System.out.println("Projeto inexistente.");
                        } else if (bookmarkmanager.userAuthorized(UserId, ProjectId) == false) {
                            System.out.println("Sem permissões.");
                        } else if (bookmarkmanager.hasDirectory(NameDir, ProjectId) == true) {
                            System.out.println("Diretório existente no projeto.");
                        } else if (bookmarkmanager.checkHierarchy(NameDir, ProjectId) == false) {
                            System.out.println("Hierarquia de diretórios inválida.");
                        } else {
                            if(bookmarkmanager.addDirectoryToProject(UserPCId, ProjectId, UserId, NameDir) == true){
                                System.out.println("Diretório criado com sucesso.");
                            } else {
                                System.out.println("Sem permissões.");
                            }
                        }
                    } else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "PRD":
                    if(commands.size() == 4) {
                        var UserPCId = commands.get(1);
                        var ProjectId = commands.get(2);
                        var UserId = commands.get(3);

                        var NameDir = scanner.nextLine();

                        var IdentificadorUtilizadorParticipante = commands.get(3);
                        if (bookmarkmanager.hasUser(UserPCId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasUser(UserId) == false) { //?
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasProject(ProjectId) == false) {
                            System.out.println("Projeto inexistente.");
                        } else if (bookmarkmanager.checkHierarchy(NameDir, ProjectId) == false) {
                            System.out.println("Hierarquia de diretórios inválida.");
                        }  else if (bookmarkmanager.hasDirectory(NameDir, ProjectId) == false) {
                        System.out.println("Hierarquia de diretórios inválida.");
                        }  else {
                            if(bookmarkmanager.removeDirectoryFromProject(UserPCId, ProjectId, UserId, NameDir) == true) {
                                System.out.println("Diretório removido com sucesso.");
                            } else {
                                System.out.println("Sem permissões.");
                            }
                        }
                    } else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "SR":
                    if(commands.size() == 3){
                        var UserId = commands.get(1);
                        var Date = commands.get(2);
                        var SessionName = scanner.nextLine();
                        if (bookmarkmanager.hasUser(UserId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.isDateValid(Date) == false) {
                            System.out.println("Data Inválida");
                        } else if (bookmarkmanager.hasSession(UserId, SessionName) == true) {
                            System.out.println("Sessão existente.");
                        } else {
                            bookmarkmanager.createSession(UserId, Date, SessionName);
                            System.out.println("Sessão criada com sucesso.");
                        }
                    }else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "SC":
                    if(commands.size() == 2) {
                        var UserId = commands.get(1);
                        var SessionName = scanner.nextLine();
                        if (bookmarkmanager.hasUser(UserId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasSession(UserId, SessionName) == false) {
                            System.out.println("Sessão inexistente.");
                        } else {
                            ArrayList a =  bookmarkmanager.checkSession(UserId, SessionName);
                            System.out.println(a.get(0));
                            for(int i = 1; i<a.size(); i++){
                                ArrayList print =(ArrayList) a.get(i);
                                System.out.println(print.get(0) + " " + print.get(1));
                            }
                        }
                    } else{
                        System.out.println("Instrução inválida.");
                        }
                    break;

                case "SAB":
                    if(commands.size() == 2) {
                        var UserSCId = commands.get(1);
                        var SessionName = scanner.nextLine();
                        var a = scanner.nextLine();
                        var IdandName = new ArrayList<>(Arrays.asList(a.split(" ")));
                        var UserId = IdandName.get(0);
                        IdandName.remove(0);
                        var BkName = String.join(" ", IdandName);

                        if (!bookmarkmanager.hasUser(UserId)) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasSession(UserSCId, SessionName) == false) {
                            System.out.println("Sessão inexistente.");
                        } else if (bookmarkmanager.hasBookmark(BkName, UserId) == false) {
                            System.out.println("Bookmark inexistente.");
                        } else {
                            if(bookmarkmanager.addBookmarkToSession(UserSCId, SessionName, UserId, BkName) == false) {
                                System.out.println("Bookmark associado a sessão com sucesso.");
                            } else {
                                System.out.println("Sem permissões.");
                            }
                        }
                    }else{
                        System.out.println("Instrução inválida.");
                    }
                    break;


                case "SRB":
                    if(commands.size() == 2) {
                        var UserSCId = commands.get(1);
                        var SessionName = scanner.nextLine();
                        var a = scanner.nextLine();
                        var IdandName = new ArrayList<>(Arrays.asList(a.split(" ")));
                        var UserId = IdandName.get(0);
                        IdandName.remove(0);
                        var BkName = String.join(" ", IdandName);

                        if (!bookmarkmanager.hasUser(UserSCId)) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasUser(UserId) == false) {
                            System.out.println("Utilizador inexistente.");
                        } else if (bookmarkmanager.hasSession(UserSCId, SessionName) == false) {
                            System.out.println("Sessão inexistente.");
                        } else if (bookmarkmanager.hasBookmark(BkName, UserId) == false) {
                            System.out.println("Bookmark inexistente na sessão.");
                        } else {
                            bookmarkmanager.removeBookmarkFromSession(UserSCId, SessionName, UserId, BkName);
                            System.out.println("Bookmark removido de sessão com sucesso.");
                        }
                    } else {
                        System.out.println("Instrução inválida.");
                    }
                        break;

                case "G":
                    if(commands.size() == 2) {
                        var nome = commands.get(1);
                        bookmarkmanager.save(nome, bookmarkmanager);
                        System.out.println("Ficheiro gravado com sucesso.");
                    } else{
                        System.out.println("Instrução inválida.");
                    }
                    break;

                case "L":
                    if(commands.size() == 2){
                        var nome = commands.get(1);
                        try {
                            bookmarkmanager = bookmarkmanager.load(nome);
                            System.out.println("Ficheiro lido com sucesso.");
                        } catch (FileNotFoundException e) {
                            System.out.println("Instrução inválida.");
                        }
                    } else{
                        System.out.println("Instrução inválida.");
                    }
                    break;
            }
        }
    }
}
