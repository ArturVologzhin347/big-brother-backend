package com.ithub.bigbrotherbackend.route

import com.ithub.bigbrotherbackend.card.CardHandler
import com.ithub.bigbrotherbackend.crud.CkudHandler
import com.ithub.bigbrotherbackend.event.EventHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ApiRoutes(
    private val eventHandler: EventHandler,
    private val crudHandler: CkudHandler,
    private val cardHandler: CardHandler
) {


    @Bean
    fun route() = coRouter {
        (accept(MediaType.APPLICATION_JSON) and "api/").nest {

            "cards/".nest {
                POST("", cardHandler::createNewCard)
            }

            "events/".nest {
                GET("", eventHandler::queryAllBy)
            }

            "skud/".nest {
                POST("", crudHandler::hanldeSkudEvent)
            }
        }
    }

}
