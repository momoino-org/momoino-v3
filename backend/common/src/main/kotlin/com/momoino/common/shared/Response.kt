package com.momoino.common.shared

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.time.Instant

open class Response(
  open val message: String = "Success",
) {
  val path: String =
    ServletUriComponentsBuilder
      .fromCurrentRequestUri()
      .build()
      .path
      .toString()
  val timestamp: Instant = Instant.now()
}

data class SingleResponse<T>(
  override val message: String = "Success",
  val data: T,
) : Response(message)

data class PaginatedResponse<T>(
  override val message: String = "Success",
  val data: T,
  val pagination: String,
) : Response(message)
