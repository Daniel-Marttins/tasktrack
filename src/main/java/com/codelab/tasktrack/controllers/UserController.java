package com.codelab.tasktrack.controllers;

import com.codelab.tasktrack.entities.User;
import com.codelab.tasktrack.exceptions.UserException;
import com.codelab.tasktrack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (UserException.UserExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    @GetMapping(value = "/find")
    public ResponseEntity<?> getUserById(@RequestParam(value = "id") Long id) {
        try {
            User user = userService.getUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (UserException.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when searching for user: " + e.getMessage());
        }
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when searching for users: " + e.getMessage());
        }
    }

    @PostMapping(value = "/find/login")
    public ResponseEntity<?> getUserByLogin(@RequestBody Map<String,  String> loginData) {
        try {
            String user = userService.getUserByLogin(loginData.get("email"), loginData.get("password"));
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (UserException.UserUnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error finding user: " + e.getMessage());
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUser(@RequestParam(value = "id") Long id, @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(id, updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (UserException.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update user: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@RequestParam(value = "id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully!");
        } catch (UserException.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete user: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }
    }

}
