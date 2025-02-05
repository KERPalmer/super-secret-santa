package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.Models.User;
import com.kenanpalmer.super_secret_santa.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user){
        try{
            return userRepository.save(user);
        }
        catch(Exception e) {
            System.out.println("ERROR IN SAVING USER FROM PRE-MADE USER INSTANCE");
            throw new RuntimeException(e);
        }
    }

    public User registerUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public User getUserById(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
