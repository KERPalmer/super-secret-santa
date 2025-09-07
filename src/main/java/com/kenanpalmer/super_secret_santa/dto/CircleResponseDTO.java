package com.kenanpalmer.super_secret_santa.dto;

import com.kenanpalmer.super_secret_santa.dto.user.UserSummaryDTO;

import java.util.List;

public class CircleResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Long ownerID;
    private List<UserSummaryDTO> users;
    private Boolean active;

    public CircleResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<UserSummaryDTO> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Long ownerID) {
        this.ownerID = ownerID;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setUsers(List<UserSummaryDTO> users) {
        this.users = users;
    }
}
