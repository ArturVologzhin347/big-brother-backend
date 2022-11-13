package com.ithub.bigbrotherbackend.router

import com.ithub.bigbrotherbackend.test.TestHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ApiRoutes(
    private val testHandler: TestHandler
) {

    @Bean
    fun route() = coRouter {
        (accept(MediaType.APPLICATION_JSON) and "/api/").nest {
            GET("/", testHandler::test)
        }
    }

}