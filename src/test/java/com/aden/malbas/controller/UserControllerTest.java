package com.aden.malbas.controller;

import com.aden.malbas.model.enums.Gender;
import com.aden.malbas.model.enums.Role;
import com.aden.malbas.model.classes.User;
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
        User user = User
                .builder()
                .id(1)
                .firstName("Omar")
                .lastName("Galal")
                .email("Omarkhaled@gmail.com")
                .password("omarkhaled")
                .gender(Gender.MALE)
                .role(Role.USER)
                .build();

        doNothing().when(userService).save(user);

        String result = userController.register(user).getBody();
        verify(userService, times(1)).save(user);

        assertEquals("User saved successfully", result);
    }

    @Test
    void editProfile() {
        User user = User
                .builder()
                .id(1)
                .firstName("Omar")
                .lastName("Galal")
                .email("Omarkhaled@gmail.com")
                .password("omarkhaled")
                .gender(Gender.MALE)
                .role(Role.USER)
                .build();

        doNothing().when(userService).update(user);

        String result = userController.editProfile(user).getBody();
        verify(userService, times(1)).update(user);

        assertEquals("User updated successfully", result);
    }

    @Test
    void deleteAccount() {
        User user = User
                .builder()
                .id(1)
                .firstName("Omar")
                .lastName("Galal")
                .email("Omarkhaled@gmail.com")
                .password("omarkhaled")
                .gender(Gender.MALE)
                .role(Role.USER)
                .build();

        doNothing().when(userService).delete(user);

        String result = userController.deleteAccount(user).getBody();
        verify(userService, times(1)).delete(user);

        assertEquals("User deleted successfully", result);
    }
}