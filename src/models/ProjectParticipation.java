package models;
import models.ProjectClass;
import models.UserClass;

import java.io.Serializable;

public class ProjectParticipation implements Serializable {
    private userType type;
    private UserClass user;
    private ProjectClass project;

    public ProjectParticipation(userType type, UserClass user, ProjectClass project){
        this.type = type;
        this.user = user;
        this.project = project;
    }

    public userType getType() {
        return this.type;
    }

    public UserClass getUser() {
        return this.user;
    }

    public ProjectClass getProject() {
        return this.project;
    }
}
