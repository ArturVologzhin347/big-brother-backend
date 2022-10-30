package com.ithub.bigbrotherbackend.route

import com.ithub.bigbrotherbackend.skud.SkudEventHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ApiRoutes(
    private val skudEventHandler: SkudEventHandler

) {

    @Bean
    fun route() = coRouter {
        (accept(MediaType.APPLICATION_JSON) and "api/").nest {

            "skud".nest {
                POST("/", skudEventHandler::sendSkudEvent)
                GET("/", skudEventHandler::queryAllSkudEvents)
            }

        }
    }

}
