package com.momoino.console.modules.auth;

import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @NonNull private final UserRepository userRepository;

    public UserDetailService(final @NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final @Nullable String username) throws UsernameNotFoundException {
        if (username == null || username.isBlank()) {
            throw new UsernameNotFoundException("Username cannot be null or blank");
        }

        return this.userRepository
                .findByUsername(username)
                .map(user -> {
                    final String[] roles = Optional.ofNullable(user.getRoles())
                            .map(roleString -> roleString.split(","))
                            .orElseGet(() -> new String[] {});

                    return User.builder()
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .roles(roles)
                            .build();
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
