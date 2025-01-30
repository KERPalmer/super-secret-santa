package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.Models.User;
import com.kenanpalmer.super_secret_santa.Repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user){
        try{
            return userRepository.save(user);
        }
        catch(Exception e) {
            System.out.println("ERROR IN SAVING USER FROM PREMADE USER INSTANCE");
            throw new RuntimeException(e);
        }
    }

    public User registerUser(String username, String password, String role){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
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
