package com.momoino.console.modules.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Optional;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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

    public void setId(@NonNull UUID id) {
        this.id = id;
    }

    @NonNull public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull public String getRoles() {
        return roles;
    }

    public void setRoles(@NonNull String roles) {
        this.roles = roles;
    }
}
