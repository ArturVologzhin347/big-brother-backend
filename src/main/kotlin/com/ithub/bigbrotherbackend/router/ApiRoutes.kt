package com.ithub.bigbrotherbackend.router

import com.ithub.bigbrotherbackend.skud.SkudHandler
import com.ithub.bigbrotherbackend.student.StudentHandler
import com.ithub.bigbrotherbackend.telegram.TelegramHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ApiRoutes(
    private val skudHandler: SkudHandler,
    private val studentHandler: StudentHandler,
    private val telegramHandler: TelegramHandler
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

            "/telegram".nest {
                POST("/buffer", telegramHandler.tryResendBufferedNotifications())

                "/auth".nest {
                    POST("/registration", telegramHandler.registration())
                    POST("/verification", telegramHandler.verification())

                    "/client/{chat}".nest {
                        GET("/status", telegramHandler.clientStatus())
                    }

                }
            }

        }
    }

}