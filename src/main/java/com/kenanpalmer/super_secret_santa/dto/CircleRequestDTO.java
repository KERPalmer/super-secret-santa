package com.kenanpalmer.super_secret_santa.dto;

import java.util.List;

public class CircleRequestDTO {
    private String name;
    private String description;
    private Long ownerID;
    private List<Long> usersID;

    public CircleRequestDTO() {
    }

    public CircleRequestDTO(String name, String description, List<Long> usersID) {
        this.name = name;
        this.description = description;
        this.usersID = usersID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getUsersID() {
        return usersID;
    }

    public void setUsersID(List<Long> usersID) {
        this.usersID = usersID;
    }

    public void addUsersID(Long userID) {
        this.usersID.add(userID);
    }

    public void clearUsers() {
        this.usersID.clear();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwner() {
        return ownerID;
    }

    public void setOwner(Long ownerID) {
        this.ownerID = ownerID;
    }
}
