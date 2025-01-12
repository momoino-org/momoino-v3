package com.momoino.console.modules.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Entity
@Table(name = "users", schema = "public")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Nullable private UUID id;

    @NonNull private String username;

    @NonNull private String password;

    @NonNull private String roles;

    public UserEntity() {
        username = "";
        password = "";
        roles = "";
    }

    public Optional<UUID> getId() {
        return Optional.ofNullable(id);
    }

    public void setId(final @NonNull UUID id) {
        this.id = id;
    }

    @NonNull public String getUsername() {
        return username;
    }

    public void setUsername(final @NonNull String username) {
        this.username = username;
    }

    @NonNull public String getPassword() {
        return password;
    }

    public void setPassword(final @NonNull String password) {
        this.password = password;
    }

    @NonNull public String getRoles() {
        return roles;
    }

    public void setRoles(final @NonNull String roles) {
        this.roles = roles;
    }
}
