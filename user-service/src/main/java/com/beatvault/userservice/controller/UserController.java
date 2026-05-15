package com.beatvault.userservice.controller;

import com.beatvault.userservice.dto.CreateUserRequest;
import com.beatvault.userservice.dto.UpdateBioRequest;
import com.beatvault.userservice.dto.UserResponse;
import com.beatvault.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest req){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") @Min(1) Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable("username") @NotBlank String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/djs")
    public ResponseEntity<List<UserResponse>> getAllDJs() {
        return ResponseEntity.ok(userService.getAllDJs());
    }

    @PatchMapping("/{id}/bio")
    public ResponseEntity<UserResponse> updateBio(
            @PathVariable("id") @Min(1) Long id,
            @Valid @RequestBody UpdateBioRequest request) {
        return ResponseEntity.ok(userService.updateBio(id, request));
    }

    @PatchMapping("/{id}/promote")
    public ResponseEntity<UserResponse> promoteToDJ(@PathVariable("id") @Min(1) Long id) {
        return ResponseEntity.ok(userService.promoteToDJ(id));
    }

    @PatchMapping("/{id}/ban")
    public ResponseEntity<UserResponse> banUser(@PathVariable("id") @Min(1) Long id) {
        return ResponseEntity.ok(userService.banUser(id));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activateUser(@PathVariable("id") @Min(1) Long id) {
        return ResponseEntity.ok(userService.activateUser(id));
    }

}
