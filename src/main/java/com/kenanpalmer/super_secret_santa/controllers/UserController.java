package com.kenanpalmer.super_secret_santa.controllers;

import com.kenanpalmer.super_secret_santa.Models.User;
import com.kenanpalmer.super_secret_santa.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user){
        return userService.registerUser(user);
    }
}
