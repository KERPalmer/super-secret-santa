package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.Models.User;
import com.kenanpalmer.super_secret_santa.Repositories.UserRepository;
import com.kenanpalmer.super_secret_santa.converter.UserToUserSummaryDTOConverter;
import com.kenanpalmer.super_secret_santa.dto.UserSummaryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserToUserSummaryDTOConverter userToUserSummaryDTOConverter;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       UserToUserSummaryDTOConverter userToUserSummaryDTOConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userToUserSummaryDTOConverter = userToUserSummaryDTOConverter;
    }

    public UserSummaryDTO registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            return userToUserSummaryDTOConverter.convert(userRepository.save(user));
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserSummaryDTO registerUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return registerUser(user);
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
