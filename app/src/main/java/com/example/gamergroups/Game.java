package com.example.gamergroups;

import java.util.ArrayList;

public class Game {
    private String gameName;
    private ArrayList<Group> gameGroups;
    private String icon;

    public Game() {
    }

    public Game(String gameName, ArrayList<Group> gameGroups, String icon) {
        this.gameName = gameName;
        this.gameGroups = gameGroups;
        this.icon = icon;
    }

    public String getGameName() {
        return gameName;
    }

    public Game setGameName(String gameName) {
        this.gameName = gameName;
        return this;
    }

    public ArrayList<Group> getGameGroups() {

        if (gameGroups == null)
            gameGroups = new ArrayList<>();

        return gameGroups;
    }

    public Game setGameGroups(ArrayList<Group> gameGroups) {
        this.gameGroups = gameGroups;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Game setIcon(String icon) {
        this.icon = icon;
        return this;
    }
}
