package com.kenanpalmer.super_secret_santa.controllers;

import com.kenanpalmer.super_secret_santa.Models.User;
import com.kenanpalmer.super_secret_santa.dto.UserSummaryDTO;
import com.kenanpalmer.super_secret_santa.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity
                .ok(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        return ResponseEntity
                .ok(userService.getUserByUsername(username));
    }

    @PostMapping()
    public ResponseEntity<UserSummaryDTO> createUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerUser(user));
    }
}
