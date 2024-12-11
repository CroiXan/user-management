package com.shop.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.shop.user.exception.ResourceNotFoundException;
import com.shop.user.exception.UserNotFoundException;
import com.shop.user.model.LoginRequest;
import com.shop.user.model.User;
import com.shop.user.service.UserService;

public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User.Builder().build(), new User.Builder().build());
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void testCreateUser() {
        User userRequest = new User.Builder().setName("Test").setEmail("test@test.com").build();
        User savedUser = new User.Builder().setId(1L).setName("Test").setEmail("test@test.com").build();
        when(userService.saveUser(any(User.class))).thenReturn(savedUser);

        ResponseEntity<User> response = userController.createUser(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedUser, response.getBody());
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User.Builder().setId(userId).setName("Test").build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUserByIdNotFound() {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userController.getUser(userId));
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        User user = new User.Builder().setId(userId).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUserById(userId);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User existingUser = new User.Builder().setId(userId).setName("Test").build();
        User updatedUser = new User.Builder().setId(userId).setName("Updated").build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(existingUser));
        when(userService.saveUser(any(User.class))).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser(userId, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    void testUpdateUserNotFound() {
        Long userId = 1L;
        User updatedUser = new User.Builder().setId(userId).build();
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userController.updateUser(userId, updatedUser));
    }

    @Test
    void testLoginUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com");
        loginRequest.setPasword("password123");
        User user = new User.Builder().setId(1L).setEmail("test@test.com").build();
        when(userService.getUserWithPassword(loginRequest.getEmail(), loginRequest.getPasword())).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserWithPassword(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testLoginUserNotFound() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com");
        loginRequest.setPasword("password123");
        when(userService.getUserWithPassword(loginRequest.getEmail(), loginRequest.getPasword())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userController.getUserWithPassword(loginRequest));
    }
}
