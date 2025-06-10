package com.market.core.user.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class User {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "userId"))
    private UserId userId;

    private String username;

    private String email;

    private UserPicture profilePicture;

    private Role userRole;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public User(UserId userId, String username, String email, UserPicture profilePicture, Role userRole, LocalDateTime createdDate) {
        this.userId = Objects.requireNonNull(userId, "UserId must not be null");
        this.username = Objects.requireNonNull(username, "Username must not be null");
        this.email = Objects.requireNonNull(email, "UserPassword must not be null");
        this.profilePicture = profilePicture; // 프로필 이미지는 null 일 수 있다.
        this.userRole = Objects.requireNonNull(userRole, "UserRole must not be null");
        this.createdDate = Objects.requireNonNull(createdDate, "User createDate must not be null");
        this.lastModifiedDate = this.createdDate;
    }

    // for Hibernate
    protected User() {
    }

    public static User create(UserIdGenerator idGenerator, String username, String email, UserPicture profilePicture, Role userRole) {

        return new User(
                idGenerator.generatorId(),
                username,
                email,
                profilePicture,
                userRole,
                LocalDateTime.now()
        );
    }

    public User update(String username, UserPicture profilePicture) {

        if (username != null) {
            this.username = username;
        }

        if (profilePicture != null) {
            this.profilePicture = profilePicture;
        }

        return this;
    }

    // Getter
    public UserId getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public UserPicture getProfilePicture() {
        return profilePicture;
    }

    public Role getUserRole() {
        return userRole;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getRoleKey() {
        return this.userRole.getKey();
    }
}
