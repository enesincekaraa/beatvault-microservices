package com.beatvault.userservice.dto;

import com.beatvault.userservice.model.User;
import com.beatvault.userservice.model.UserRole;
import com.beatvault.userservice.model.UserStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final UserRole role;
    private final UserStatus status;
    private final String bio;
    private final int followerCount;
    private final LocalDateTime createdAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.bio = user.getBio();
        this.followerCount = user.getFollowerCount();
        this.createdAt = user.getCreatedAt();
    }
}