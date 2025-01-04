package com.momoino.console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
	@Bean
	SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
				.formLogin(Customizer.withDefaults())
				.oauth2Login(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user")
				.password("user")
				.roles("USER")
				.build();

		return new MapReactiveUserDetailsService(user);
	}
}
