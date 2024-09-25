package com.example.todolist.controller;

import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.model.User;
import com.example.todolist.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // endpoint for reading a user by id
    // accessible by admin and each user for their id
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> readUser(@PathVariable Integer userId) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    // endpoint for reading all users
    // accessible by admin
    @GetMapping("/users")
    public ResponseEntity<List<User>> readUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // endpoint for creating a user
    // accessible by anyone
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    // endpoint for updating a user
    // accessible by admin and each user for their id
    @PutMapping("/users/{userId}") // add id to endpoint and remove from body
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user, @PathVariable Integer userId)
            throws ResourceNotFoundException {
        user.setUserId(userId);
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    // endpoint for deleting a user by id
    // accessible by admin and each user for their id
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) throws ResourceNotFoundException {
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}