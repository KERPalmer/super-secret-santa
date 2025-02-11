package com.kenanpalmer.super_secret_santa.dto;

import com.kenanpalmer.super_secret_santa.Models.Circle;

import java.util.Set;
import java.util.stream.Collectors;

public class CircleDTO {
    private Long id;
    private String name;
    private Set<UserSummaryDTO> users;

    public CircleDTO() {
    }

    public CircleDTO(Circle circle) {
        id = circle.getId();
        name = circle.getName();
        users = circle.getUsers().stream().map(UserSummaryDTO::new)
                .collect(Collectors.toSet());
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
}
