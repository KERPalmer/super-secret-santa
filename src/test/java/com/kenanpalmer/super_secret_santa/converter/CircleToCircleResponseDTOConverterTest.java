package com.kenanpalmer.super_secret_santa.converter;

import com.kenanpalmer.super_secret_santa.dto.CircleResponseDTO;
import com.kenanpalmer.super_secret_santa.dto.user.UserSummaryDTO;
import com.kenanpalmer.super_secret_santa.exception.CircleConversionException;
import com.kenanpalmer.super_secret_santa.models.Circle;
import com.kenanpalmer.super_secret_santa.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CircleToCircleResponseDTOConverterTest {

    private UserToUserSummaryDTOConverter userToUserSummaryDTOConverter;
    private CircleToCircleResponseDTOConverter converter;

    private static final long CIRCLE_ID = 1L;
    private static final String CIRCLE_NAME = "TEST_NAME";
    private static final String CIRCLE_DESCRIPTION = "TEST_DESCRIPTION";

    private static final long OWNER_ID = 2L;
    private static final long MEMBER_ID = 3L;

    @BeforeEach
    void setUp() {
        userToUserSummaryDTOConverter = Mockito.mock(UserToUserSummaryDTOConverter.class);
        converter = new CircleToCircleResponseDTOConverter(userToUserSummaryDTOConverter);
    }

    @Test
    void convert_shouldMapCircleToResponseDTO() {
        User member = new User();
        member.setId(MEMBER_ID);

        Circle circle = getCircle();
        circle.setUsers(Set.of(member));

        UserSummaryDTO mockUserSummary = new UserSummaryDTO();
        mockUserSummary.setId(MEMBER_ID);
        when(userToUserSummaryDTOConverter.convert(member)).thenReturn(mockUserSummary);

        CircleResponseDTO result = converter.convert(circle);

        assertNotNull(result);
        assertEquals(CIRCLE_ID, result.getId());
        assertEquals(CIRCLE_NAME, result.getName());
        assertEquals(CIRCLE_DESCRIPTION, result.getDescription());
        assertTrue(result.getActive());
        assertEquals(OWNER_ID, result.getOwnerID());
        assertEquals(1, result.getUsers().size());
        assertEquals(MEMBER_ID, result.getUsers().getFirst().getId());
    }

    @Test
    void shouldReturnEmptyUserListWhenNoMembers(){
        Circle emptyCircle = getCircle();
        emptyCircle.setUsers(new HashSet<>() {});

        CircleResponseDTO result = converter.convert(emptyCircle);

        assertNotNull(result);
        assertEquals(CIRCLE_ID, result.getId());
        assertEquals(CIRCLE_NAME, result.getName());
        assertEquals(CIRCLE_DESCRIPTION, result.getDescription());
        assertTrue(result.getActive());
        assertEquals(OWNER_ID, result.getOwnerID());

        //empty list when no users
        assertNotNull(result.getUsers());
        assertTrue(result.getUsers().isEmpty());

    }

    @Test
    void convert_shouldThrowCircleConversionExceptionOnError() {
        // Arrange
        Circle badCircle = new Circle(); // Missing owner -> will cause Null Point Exception

        // Act & Assert
        assertThrows(CircleConversionException.class, () -> converter.convert(badCircle));
    }



    private Circle getCircle() {
        //create circle
        User owner = new User();
        owner.setId(OWNER_ID);

        Circle circle = new Circle();
        circle.setId(CIRCLE_ID);
        circle.setName(CIRCLE_NAME);
        circle.setDescription(CIRCLE_DESCRIPTION);
        circle.setActive(true);
        circle.setOwner(owner);
        return circle;
    }
}
