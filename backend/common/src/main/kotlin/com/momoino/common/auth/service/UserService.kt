package com.momoino.common.auth.service

import com.momoino.common.auth.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
  private val userRepository: UserRepository,
) : UserDetailsService {
  override fun loadUserByUsername(username: String?): UserDetails {
    if (username.isNullOrBlank()) {
      throw UsernameNotFoundException("Username cannot be null or blank")
    }

    val userEntity = this.userRepository.findByUsername(username) ?: throw UsernameNotFoundException("Username not found")

    return User
      .builder()
      .username(userEntity.username)
      .password(userEntity.password)
      .roles(userEntity.roles)
      .build()
  }
}
