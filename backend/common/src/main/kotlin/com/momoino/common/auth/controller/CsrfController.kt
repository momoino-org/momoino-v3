package com.momoino.common.auth.controller

import com.momoino.common.shared.SingleResponse
import org.springframework.http.HttpStatus
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CsrfController {
  @GetMapping("/api/v1/csrf")
  @ResponseStatus(HttpStatus.OK)
  fun csrf(csrfToken: CsrfToken): SingleResponse<CsrfToken> = SingleResponse(data = csrfToken)
}
