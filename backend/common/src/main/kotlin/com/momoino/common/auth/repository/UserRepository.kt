package com.momoino.common.auth.repository

import com.momoino.common.auth.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<UserEntity, UUID> {
  fun findByUsername(username: String): UserEntity?
}
