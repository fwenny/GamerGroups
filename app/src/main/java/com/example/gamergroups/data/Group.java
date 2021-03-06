package com.example.gamergroups.data;

import java.util.ArrayList;

// holds group info
public class Group {
    private String groupName;
    private String groupDescription;
    private String game;
    private int numberOfUsers;
    private ArrayList<User> usersInGroup;

    public Group() {
    }

    public Group(String groupName, String groupDescription, String game, int numberOfUsers, ArrayList<User> usersInGroup) {
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

    public Group setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
        return this;
    }

    public String getGame() {
        return game;
    }

    public Group setGame(String game) {
        this.game = game;
        return this;
    }

    public ArrayList<User> getUsersInGroup() {
        if (usersInGroup == null)
            usersInGroup = new ArrayList<>();

        return usersInGroup;
    }

    public Group setUsersInGroup(ArrayList<User> usersInGroup) {
        this.usersInGroup = usersInGroup;
        return this;
    }

    // remove user from group
    public void removeUser(User u) {
        // remove user by email
        this.usersInGroup.removeIf(user -> user.getEmail().equals(u.getEmail()));
        this.setNumberOfUsers(this.usersInGroup.size());
    }

    // add user to group
    public void addUser(User u) {
        // check email to make sure user doesn't already exist
        if (this.usersInGroup.stream().noneMatch(user -> user.getEmail().equals(u.getEmail()))) {
            this.usersInGroup.add(u);
        }

        this.setNumberOfUsers(this.usersInGroup.size());
    }

    public boolean isUserInGroup(User u){
        if (u != null){
            return this.getUsersInGroup().stream().anyMatch(user -> user.getEmail().equals(u.getEmail()));
        }

        return false;
    }
}
