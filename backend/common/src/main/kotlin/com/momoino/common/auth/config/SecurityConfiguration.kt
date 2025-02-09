package com.momoino.common.auth.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler
import org.springframework.security.web.csrf.CsrfTokenRequestHandler
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler
import java.util.function.Supplier

class SpaCsrfTokenRequestHandler : CsrfTokenRequestHandler {
  private val plain: CsrfTokenRequestHandler = CsrfTokenRequestAttributeHandler()
  private val xor: CsrfTokenRequestHandler = XorCsrfTokenRequestAttributeHandler()

  override fun handle(
    request: HttpServletRequest,
    response: HttpServletResponse,
    csrfToken: Supplier<CsrfToken>,
  ) {
    xor.handle(request, response, csrfToken)
    csrfToken.get()
  }

  override fun resolveCsrfTokenValue(
    request: HttpServletRequest,
    csrfToken: CsrfToken,
  ): String? {
    val headerValue: String? = request.getHeader(csrfToken.headerName)
    val requestHandler = if (headerValue?.isNotBlank() == true) plain else xor

    return requestHandler.resolveCsrfTokenValue(request, csrfToken)
  }
}

@Configuration
@EnableWebSecurity
class SecurityConfiguration {
  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .csrf {
        it.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        it.csrfTokenRequestHandler(SpaCsrfTokenRequestHandler())
      }.authorizeHttpRequests {
        it.anyRequest().authenticated()
      }.formLogin(Customizer.withDefaults())
      .oauth2Login(Customizer.withDefaults())

    return http.build()
  }
}
