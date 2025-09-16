package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.converter.UserToUserSummaryDTOConverter;
import com.kenanpalmer.super_secret_santa.dto.user.UserSummaryDTO;
import com.kenanpalmer.super_secret_santa.exception.UserIdNotFoundException;
import com.kenanpalmer.super_secret_santa.exception.UserRegistrationException;
import com.kenanpalmer.super_secret_santa.exception.UsernameAlreadyRegisteredException;
import com.kenanpalmer.super_secret_santa.exception.UsernameNotFoundException;
import com.kenanpalmer.super_secret_santa.models.User;
import com.kenanpalmer.super_secret_santa.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceUnitTest {

    private static final String TEST_USERNAME = "TEST_USERNAME";
    private static final String TEST_PASSWORD = "TEST_PASSWORD";
    private static final String TEST_ENCODED_PASSWORD = "TEST_ENCODED_PASSWORD";


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserToUserSummaryDTOConverter userToUserSummaryDTOConverter;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserSummaryDTO userSummaryDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername(TEST_USERNAME);
        user.setPassword(TEST_PASSWORD);

        userSummaryDTO = new UserSummaryDTO();
        userSummaryDTO.setId(1L);
        userSummaryDTO.setUsername(TEST_USERNAME);
    }

    @Test
    void registerUser_success() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(TEST_PASSWORD)).thenReturn(TEST_ENCODED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userToUserSummaryDTOConverter.convert(user)).thenReturn(userSummaryDTO);

        Optional<UserSummaryDTO> result = userService.registerUser(user);

        assertTrue(result.isPresent());
        assertEquals(TEST_USERNAME, result.get().getUsername());
        verify(passwordEncoder).encode(TEST_PASSWORD);
        verify(userRepository).save(user);
        verify(userToUserSummaryDTOConverter).convert(user);
    }

    @Test
    void registerUser_failureThrowsException() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(TEST_PASSWORD)).thenReturn(TEST_ENCODED_PASSWORD);
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("DB error"));

        assertThrows(UserRegistrationException.class, () -> userService.registerUser(user));
    }
    @Test
    void registerUser_UsernameAlreadyExistsThrowsException() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(UsernameAlreadyRegisteredException.class, () -> userService.registerUser(user));
    }

    @Test
    void findAllUsers_returnsList() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = userService.findAllUsers();

        assertEquals(1, result.size());
        assertEquals(TEST_USERNAME, result.getFirst().getUsername());
    }

    @Test
    void findUserByID_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findUserByID(1L);

        assertEquals(TEST_USERNAME, result.getUsername());
    }

    @Test
    void findUserByID_notFoundThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserIdNotFoundException.class, () -> userService.findUserByID(1L));
    }

    @Test
    void findUserByUsername_found() {
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.ofNullable(user));

        User result = userService.findUserByUsername(TEST_USERNAME);

        assertNotNull(result);
        assertEquals(TEST_USERNAME, result.getUsername());
    }

    @Test
    void findUserByUsername_notFoundReturnsNull() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class , () -> {
            userService.findUserByUsername("unknown");
        });
    }

    @Test
    void findAllUsersByIDs_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<User> result = userService.findListOfUsersByIDs(List.of(1L));

        assertEquals(1, result.size());
        assertEquals(TEST_USERNAME, result.getFirst().getUsername());
    }

    @Test
    void searchByUsername_noMatchReturnsEmpty() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserSummaryDTO> result = userService.searchByUsername("nomatch");

        assertTrue(result.isEmpty());
    }
}
