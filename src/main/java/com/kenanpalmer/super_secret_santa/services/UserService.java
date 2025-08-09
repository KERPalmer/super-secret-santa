package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.converter.UserToUserSummaryDTOConverter;
import com.kenanpalmer.super_secret_santa.dto.user.UserSummaryDTO;
import com.kenanpalmer.super_secret_santa.models.User;
import com.kenanpalmer.super_secret_santa.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            return Optional.ofNullable(userToUserSummaryDTOConverter.convert(userRepository.save(user)));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    public User getUserById(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findAllUsersById(List<Long> userIds) {
        return userRepository.findAllById(userIds).stream().toList();
    }

    public User findUserByID(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> findUsersByIDs(List<Long> ids){
        return userRepository.findAllById(ids);
    }
}
