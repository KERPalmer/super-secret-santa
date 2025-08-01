package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.converter.UserToUserSummaryDTOConverter;
import com.kenanpalmer.super_secret_santa.dto.user.UserSummaryDTO;
import com.kenanpalmer.super_secret_santa.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.kenanpalmer.super_secret_santa.repositories.UserRepository;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class UserServiceUnitTests {

    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String ENCODED_PASSWORD = "ENCODED_PASSWORD";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserToUserSummaryDTOConverter userToUserSummaryDTOConverter;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserWithUserObject(){
        User user = getUser();

        when(passwordEncoder.encode(user.getPassword())).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(user)).thenReturn(user);
        when(userToUserSummaryDTOConverter.convert(user))
                .thenReturn(new UserSummaryDTO(user));
        UserSummaryDTO result = userService.registerUser(user).get();

        assertThat(result).isInstanceOf(UserSummaryDTO.class);
        assertThat(result.getUsername()).isEqualTo(user.getUsername());

    }



    private User getUser(){
        return new User(USERNAME, PASSWORD);
    }

}


