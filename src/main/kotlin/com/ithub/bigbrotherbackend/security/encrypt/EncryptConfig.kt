package com.ithub.bigbrotherbackend.security.encrypt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class EncryptConfig {

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

}
