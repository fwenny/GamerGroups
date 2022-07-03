package com.example.gamergroups;

import java.util.ArrayList;

public class Group {
    private String groupName;
    private String groupDescription;
    private String game;
    private int numberOfUsers;
    private ArrayList<String> usersInGroup;

    public Group() {
    }

    public Group(String groupName, String groupDescription, String game, int numberOfUsers, ArrayList<String> usersInGroup) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.game = game;
        this.numberOfUsers = numberOfUsers;
        this.usersInGroup = usersInGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public Group setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public Group setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
        return this;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public String getGame() {
        return game;
    }

    public Group setGame(String game) {
        this.game = game;
        return this;
    }

    public Group setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
        return this;
    }

    public ArrayList<String> getUsersInGroup() {
        return usersInGroup;
    }

    public Group setUsersInGroup(ArrayList<String> usersInGroup) {
        this.usersInGroup = usersInGroup;
        return this;
    }
}
