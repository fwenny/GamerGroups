package com.example.gamergroups;

public class User {
    private String email;
    private String displayName;
    private String iconURL;

    public User() {
    }

    public User(String email, String displayName, String iconURL) {
        this.email = email;
        this.displayName = displayName;
        this.iconURL = iconURL;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
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
