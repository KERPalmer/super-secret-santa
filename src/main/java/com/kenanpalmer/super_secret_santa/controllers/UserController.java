package com.kenanpalmer.super_secret_santa.controllers;

import com.kenanpalmer.super_secret_santa.dto.user.UserSummaryDTO;
import com.kenanpalmer.super_secret_santa.models.User;
import com.kenanpalmer.super_secret_santa.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserSummaryDTO>> searchUserByUsername(@RequestParam String query){
        return ResponseEntity.ok(userService.searchByUsername(query));
    }
}
