package com.example.employees.model;

public class User {
    private int id;
    private String login;
    private String password;
    private ROLE role;

    public enum ROLE {
        USER, ADMIN, UNKNOWN
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }
}
