package edu.upao.pe.ilovetravelfinal.controllers;

import edu.upao.pe.ilovetravelfinal.models.User;

public class ApiComment {

    private String comment;
    private User user;

    public ApiComment(String comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}