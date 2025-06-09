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

    private String password;

    private UserPicture profilePicture;

    private Role userRole;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public User(UserId userId, String username, String password, UserPicture profilePicture, Role userRole, LocalDateTime createdDate) {
        this.userId = Objects.requireNonNull(userId, "UserId must not be null");
        this.username = Objects.requireNonNull(username, "Username must not be null");
        this.password = Objects.requireNonNull(password, "UserPassword must not be null");
        this.profilePicture = Objects.requireNonNull(profilePicture, "ProfilePicture must not be null");
        this.userRole = Objects.requireNonNull(userRole, "UserRole must not be null");
        this.createdDate = Objects.requireNonNull(createdDate, "User createDate must not be null");
        this.lastModifiedDate = this.createdDate;
    }

    // for Hibernate
    public User() {
    }

    public static User create(UserIdGenerator idGenerator, String username, String password, UserPicture profilePicture, Role userRole) {

        return new User(
                idGenerator.generatorId(),
                username,
                password,
                profilePicture,
                userRole,
                LocalDateTime.now()
        );
    }

    public void update(String username, String password, UserPicture profilePicture, Role userRole) {

        if (username != null) {
            this.username = username;
        }

        if (password != null) {
            this.password = password;
        }

        if (profilePicture != null) {
            this.profilePicture = profilePicture;
        }

        if (userRole != null) {
            this.userRole = userRole;
        }
    }

    // Getter
    public UserId getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
}
