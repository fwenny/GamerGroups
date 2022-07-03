package com.example.gamergroups;

import java.util.ArrayList;

public class User {
    private String email;
    private String displayName;
    private String iconURL;
    private ArrayList<String> groups;

    public User() {
    }

    public User(String email, String displayName, String iconURL, ArrayList<String> groups) {
        this.email = email;
        this.displayName = displayName;
        this.iconURL = iconURL;
        this.groups = groups;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public User setGroups(ArrayList<String> groups) {
        this.groups = groups;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public User setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getIconURL() {
        return iconURL;
    }

    public User setIconURL(String iconURL) {
        this.iconURL = iconURL;
        return this;
    }
}
