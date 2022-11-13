package com.ithub.bigbrotherbackend.security

import com.ithub.bigbrotherbackend.Profiles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    @Profile(Profiles.DEV)
    fun devSpringSecurityChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http.apply {
            formLogin().disable()
            httpBasic().disable()
            cors().disable()
            csrf { it.disable() }
        }.build()
    }

    @Bean
    @Profile(Profiles.PROD)
    fun prodSpringSecurityChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http.apply {
            csrf().csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
            // TODO
        }.build()
    }

}
