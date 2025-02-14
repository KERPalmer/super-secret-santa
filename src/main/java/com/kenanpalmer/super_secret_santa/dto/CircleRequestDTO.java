package com.kenanpalmer.super_secret_santa.dto;

import java.util.List;

public class CircleRequestDTO {
    private String name;
    private List<Long> usersID;

    public CircleRequestDTO() {
    }

    public CircleRequestDTO(List<Long> usersID, String name) {
        this.usersID = usersID;
        this.name = name;
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

    public void clearUsers(){
        this.usersID.clear();
    }

}
