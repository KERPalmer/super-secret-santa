package com.kenanpalmer.super_secret_santa.dto;

import com.kenanpalmer.super_secret_santa.dto.user.UserSummaryDTO;
import com.kenanpalmer.super_secret_santa.models.Circle;
import com.kenanpalmer.super_secret_santa.models.User;

import java.util.Set;
import java.util.stream.Collectors;

public class CircleDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private Set<UserSummaryDTO> users;
    private User owner;

    public CircleDTO() {
    }

    public CircleDTO(Long id, String name, String description, Boolean active, Set<UserSummaryDTO> users, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.users = users;
        this.owner = owner;
    }

    public CircleDTO(Circle circle) {
        this.id = circle.getId();
        this.name = circle.getName();
        this.description = circle.getDescription();
        this.active = circle.getActive();
        this.users = circle.getUsers().stream().map(UserSummaryDTO::new)
                .collect(Collectors.toSet());
        this.owner = circle.getOwner();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserSummaryDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserSummaryDTO> users) {
        this.users = users;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
