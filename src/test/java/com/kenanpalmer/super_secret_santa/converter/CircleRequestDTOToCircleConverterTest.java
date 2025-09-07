package com.kenanpalmer.super_secret_santa.converter;

import com.kenanpalmer.super_secret_santa.dto.CircleRequestDTO;
import com.kenanpalmer.super_secret_santa.exception.CircleConversionException;
import com.kenanpalmer.super_secret_santa.models.Circle;
import com.kenanpalmer.super_secret_santa.models.User;
import com.kenanpalmer.super_secret_santa.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CircleRequestDTOToCircleConverterTest {


    public static final String TEST_OWNER_USERNAME = "TEST_USERNAME";
    public static final String TEST_DESCRIPTION = "TEST_DESCRIPTION";
    public static final String TEST_CIRCLE_NAME = "TEST_CIRCLE_NAME";
    private UserService userService;
    private CircleRequestDTOToCircleConverter converter;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        converter = new CircleRequestDTOToCircleConverter(userService);

        //mock security for getting logged-in user
        SecurityContext context = mock(SecurityContext.class);

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder =
                     Mockito.mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(context);
        }
    }

    @Test
    void convertsCircleRequestDTOToCircleSuccessfully(){

        CircleRequestDTO circleRequestDTO = getCircleRequestDTO();

        User owner = new User();
        owner.setUsername(TEST_OWNER_USERNAME);
        when(userService.findUserByUsername(TEST_OWNER_USERNAME))
                .thenReturn(owner);

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        circleRequestDTO.setUsersID(Arrays.asList(1L,2L));

        when(userService.findAllUsersByIDs(circleRequestDTO.getUsersID()))
                .thenReturn(Arrays.asList(user1, user2));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(TEST_OWNER_USERNAME);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder =
                     Mockito.mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(context);

            //testing
            Circle circle = converter.convert(circleRequestDTO);

            assertNotNull(circle);
            assertEquals(TEST_CIRCLE_NAME, circle.getName());
            assertEquals(TEST_DESCRIPTION, circle.getDescription());
            assertTrue(circle.getActive());
            assertEquals(2, circle.getUsers().size());
            assertEquals(owner, circle.getOwner());
        }
    }

    @Test
    void emptyListReturnedWhenNoCircleMembers(){
        CircleRequestDTO circleRequestDTO = getCircleRequestDTO();

        User owner = new User();
        owner.setUsername(TEST_OWNER_USERNAME);

        when(userService.findUserByUsername(TEST_OWNER_USERNAME))
                .thenReturn(owner);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(TEST_OWNER_USERNAME);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder =
                     Mockito.mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(context);

            //testing
            Circle circle = converter.convert(circleRequestDTO);

            assertNotNull(circle);
            assertEquals(TEST_CIRCLE_NAME, circle.getName());
            assertEquals(TEST_DESCRIPTION, circle.getDescription());
            assertTrue(circle.getActive());
            assertEquals(0, circle.getUsers().size());
            assertEquals(owner, circle.getOwner());
        }
    }

    @Test
    void convertShouldThrowCircleConversionException(){
        CircleRequestDTO circleRequestDTO = new CircleRequestDTO();
        User owner = new User();
        owner.setUsername(TEST_OWNER_USERNAME);

        Long badID = 1L;
        circleRequestDTO.setUsersID(List.of(badID));
        when(userService.findAllUsersByIDs(circleRequestDTO.getUsersID()))
                .thenThrow(new NoSuchElementException("User ID not Found"));
        SecurityContext context = mock(SecurityContext.class);

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder =
                     Mockito.mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(context);

            //testing
            assertThrows(CircleConversionException.class, () -> converter.convert(circleRequestDTO));
        }
    }
    
    private CircleRequestDTO getCircleRequestDTO(){
        CircleRequestDTO dto = new CircleRequestDTO();
        dto.setName(TEST_CIRCLE_NAME);
        dto.setDescription(TEST_DESCRIPTION);
        return dto;
    }


}