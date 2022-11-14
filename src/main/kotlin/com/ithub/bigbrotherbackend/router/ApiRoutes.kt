package com.ithub.bigbrotherbackend.router

import com.ithub.bigbrotherbackend.skud.SkudHandler
import com.ithub.bigbrotherbackend.student.StudentHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ApiRoutes(
    private val skudHandler: SkudHandler,
    private val studentHandler: StudentHandler
) {

    @Bean
    fun route() = coRouter {
        (accept(MediaType.APPLICATION_JSON) and "/api").nest {

            "/skud".nest {
                POST("", skudHandler.acceptSkudEvent())
            }

            "/admin".nest {

                "/skud".nest {
                    GET("", skudHandler.queryAll())
                }

                "/students".nest {
                    GET("", studentHandler.queryAll())
                }

            }

        }
    }

}