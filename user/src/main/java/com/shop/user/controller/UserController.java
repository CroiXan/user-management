package com.shop.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.user.exception.ResourceNotFoundException;
import com.shop.user.model.User;
import com.shop.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User userRequest) {
        User newUser = new User.Builder()
            .setName(userRequest.getName())
            .setEmail(userRequest.getEmail())
            .setPassword(userRequest.getPassword())
            .setPhone(userRequest.getPhone())
            .setRole("USER")
            .build();
        User createdUser = userService.saveUser(newUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User con ID "+ id +" no se encuentra"));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.getUserById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User con ID "+ id +" no se encuentra"));
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User updatedUser ) {
        User order = userService.getUserById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User con ID "+ id +" no se encuentra"));
        User setBuildOrder = new User.Builder()
            .setId(order.getId_user())
            .setName(updatedUser.getName())
            .setEmail(updatedUser.getEmail())
            .setPassword(updatedUser.getPassword())
            .setPhone(updatedUser.getPhone())
            .setRole(updatedUser.getRole())
            .build();
        User orderResult = userService.saveUser(setBuildOrder);
        return ResponseEntity.ok(orderResult);
    }
}
