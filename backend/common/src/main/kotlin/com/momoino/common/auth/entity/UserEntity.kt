package com.momoino.common.auth.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users", schema = "public")
class UserEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  val id: UUID? = null,
  val username: String,
  val password: String,
  val roles: String,
)
