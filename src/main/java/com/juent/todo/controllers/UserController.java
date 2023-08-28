package com.juent.todo.controllers;

import com.juent.todo.models.User;
import com.juent.todo.services.UserService;
import com.juent.todo.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        if (userService.isUsernameTaken(user.getUsername())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, null, "Username is already taken."));
        }
        if (userService.isEmailTaken(user.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, null, "Email is already in use."));
        }
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(new ApiResponse<>(true, savedUser, "User created successfully."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, user, "User found.")))
                .orElse(ResponseEntity.badRequest().body(new ApiResponse<>(false, null, "User not found.")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        if(userService.existsById(id)) {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse<>(true, null, "User deleted successfully."));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, null, "User not found."));
        }
    }
}