package com.musicplaylist.spring.soap.api.service;

import java.util.List;

public class loggedUserList {

    private List<LoggedUser> loggedUsers;

    public List<LoggedUser> getLoggedUsers() {
        return loggedUsers;
    }

    public void setLoggedUsers(List<LoggedUser> loggedUsers) {
        this.loggedUsers = loggedUsers;
    }
}
