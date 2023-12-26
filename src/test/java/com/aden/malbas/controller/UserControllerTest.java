/*
package com.aden.malbas.controller;

import com.aden.malbas.dto.UserDTO;
import com.aden.malbas.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private UserController userController;
    @MockBean
    private UserService userService;

    @Test
    void registerTest() {
        UserDTO user = UserDTO
                .builder()
                .firstName("Omar")
                .lastName("Galal")
                .email("Omarkhaled@gmail.com")
                .password("omarkhaled")
                .gender("male")
                .build();

        doNothing().when(userService).register(user);

        String result = userController.register(user).getBody();
        verify(userService, times(1)).register(user);

        assertEquals("User saved successfully", result);
    }

    @Test
    void editProfile() {
        UserDTO user = UserDTO
                .builder()
                .firstName("Omar")
                .lastName("Galal")
                .email("Omarkhaled@gmail.com")
                .password("omarkhaled")
                .gender("male")
                .build();

        doNothing().when(userService).update(user);

        String result = userController.editProfile(user).getBody();
        verify(userService, times(1)).update(user);

        assertEquals("User updated successfully", result);
    }

    @Test
    void deleteAccount() {
        Integer userId = 1;

        doNothing().when(userService).deleteAccount(userId);

        String result = userController.deleteAccount(userId).getBody();
        verify(userService, times(1)).deleteAccount(userId);

        assertEquals("User deleted successfully", result);
    }
}
*/
