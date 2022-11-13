package com.ithub.bigbrotherbackend.route

import com.ithub.bigbrotherbackend.skud.SkudEventHandler
import com.ithub.bigbrotherbackend.telegram.TelegramHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ApiRoutes(
    private val skudEventHandler: SkudEventHandler,
    private val telegramHandler: TelegramHandler
) {

    @Bean
    fun route() = coRouter {
        (accept(MediaType.APPLICATION_JSON) and "api/").nest {

            "skud".nest {
                POST("", skudEventHandler::handleSkudEvent)
            }

            "admin".nest {
                GET("", skudEventHandler::findAll)
            }

            "telegram".nest {
                POST("registration", telegramHandler::registration)
                POST("verification", telegramHandler::verification)
            }

        }
    }

}
