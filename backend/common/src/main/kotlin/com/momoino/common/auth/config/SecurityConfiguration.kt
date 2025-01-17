package com.momoino.common.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration {
  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .csrf(Customizer.withDefaults())
      .authorizeHttpRequests { it.anyRequest().authenticated() }
      .formLogin(Customizer.withDefaults())
      .oauth2Login(Customizer.withDefaults())

    return http.build()
  }
}
