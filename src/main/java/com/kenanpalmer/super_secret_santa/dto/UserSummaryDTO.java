package com.kenanpalmer.super_secret_santa.dto;

import com.kenanpalmer.super_secret_santa.models.User;

public class UserSummaryDTO {
    private Long id;
    private String username;


    public UserSummaryDTO(){}

    public UserSummaryDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserSummaryDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
