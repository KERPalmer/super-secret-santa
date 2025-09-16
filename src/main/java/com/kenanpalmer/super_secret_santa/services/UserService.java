package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.converter.UserToUserSummaryDTOConverter;
import com.kenanpalmer.super_secret_santa.dto.user.UserSummaryDTO;
import com.kenanpalmer.super_secret_santa.exception.UserIdNotFoundException;
import com.kenanpalmer.super_secret_santa.exception.UserRegistrationException;
import com.kenanpalmer.super_secret_santa.exception.UsernameAlreadyRegisteredException;
import com.kenanpalmer.super_secret_santa.exception.UsernameNotFoundException;
import com.kenanpalmer.super_secret_santa.models.User;
import com.kenanpalmer.super_secret_santa.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserToUserSummaryDTOConverter userToUserSummaryDTOConverter;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       UserToUserSummaryDTOConverter userToUserSummaryDTOConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userToUserSummaryDTOConverter = userToUserSummaryDTOConverter;
    }

    public Optional<UserSummaryDTO> registerUser(User user) {

        checkIfUsernameAvailable(user);

        //encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //save user
        try {
            return Optional.ofNullable(userToUserSummaryDTOConverter.convert(userRepository.save(user)));
        } catch (RuntimeException e){
            throw new UserRegistrationException("Error Creating User", e);
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserByID(Long id) {
        try{
            return userRepository.findById(id).orElseThrow();
        }
        catch(NoSuchElementException e){
            throw new UserIdNotFoundException("User ID Not Found", e);
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    public List<User> findListOfUsersByIDs(List<Long> ids){
        return ids.stream().map(this::findUserByID).toList();
    }

    public List<UserSummaryDTO> searchByUsername(String query){
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().contains(query))
                .map(userToUserSummaryDTOConverter::convert).toList();
    }

    private void checkIfUsernameAvailable(User user) {
        //check if username already registered
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            logger.error("username already in use {}", user.getUsername());
            throw new UsernameAlreadyRegisteredException("Username already registered");
        }
    }
}
