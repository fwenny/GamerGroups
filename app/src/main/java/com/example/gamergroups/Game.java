package com.example.gamergroups;

import java.util.ArrayList;

public class Game {
    private String gameName;
    private ArrayList<String> gameGroups;
    private String icon;

    public Game() {
    }

    public Game(String gameName, ArrayList<String> gameGroups, String icon) {
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

    public ArrayList<String> getGameGroups() {
        return gameGroups;
    }

    public Game setGameGroups(ArrayList<String> gameGroups) {
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
