package com.beatvault.userservice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    private String bio;

    private int followerCount;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role=UserRole.LISTENER;
        this.status=UserStatus.ACTIVE;
        this.followerCount=0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public void promoteToDj(){
//        if (this.followerCount<10){
//            throw new IllegalStateException("DJ olmak için en az 10 takipçi gerekli");
//        }
        this.role=UserRole.DJ;
        this.updatedAt = LocalDateTime.now();
    }

    public void ban(){
        if (this.role==UserRole.ADMIN){
            throw new IllegalStateException("Admin kullanıcı banlanamaz");
        }
        this.status=UserStatus.BANNED;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate(){
        this.status=UserStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }
    public void updateBio(String bio) {
        if (bio != null && bio.length() > 300) {
            throw new IllegalArgumentException("Bio 300 karakterden uzun olamaz");
        }
        this.bio = bio;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementFollowerCount() {
        this.followerCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isDJ() {
        return this.role == UserRole.DJ;
    }

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }


}
