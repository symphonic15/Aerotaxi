package controllers;

import models.users.User;

public class UsersController {
    private Core core;
    private User session;

    public UsersController(Core core) {
        this.core = core;
    }

    public boolean signUp(User user) {
        if(this.getUserByDni(user.getDni()) == null) {
            this.core.getJsonDB().insert(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean login(String dni, String password) {
        User user = this.getUserByDni(dni);

        if(user != null && validatePassword(user, password)) {
            this.session = user;
            return true;
        } else {
            return false;
        }
    }

    /* Helpers */

    public User getUserByDni(String dni) {
        return (User) this.core.getJsonDB().find(dni, User.class);
    }

    public boolean validatePassword(User user, String password) {
        if(user.getPassword().equals(password))
            return true;
        return false;
    }

    /* Getters and setters */

    public User getSession() {
        return session;
    }

    public void setSession(User session) {
        this.session = session;
    }
}
