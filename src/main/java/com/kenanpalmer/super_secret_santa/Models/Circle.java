package com.kenanpalmer.super_secret_santa.Models;

import com.kenanpalmer.super_secret_santa.dto.UserSummaryDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Circle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "CircleMembers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "circle_id")
    )
    private Set<User> users = new HashSet<>();

    public Circle(){}
    public Circle(String circleName){
        this.name = circleName;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUserToCircle(User user){
        users.add(user);
    }
}
