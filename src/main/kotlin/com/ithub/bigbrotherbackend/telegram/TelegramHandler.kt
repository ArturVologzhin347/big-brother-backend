package com.ithub.bigbrotherbackend.telegram

import com.fasterxml.jackson.databind.ser.Serializers.Base
import com.ithub.bigbrotherbackend.base.hanlder.BaseHandler
import com.ithub.bigbrotherbackend.base.hanlder.BaseRequest
import com.ithub.bigbrotherbackend.telegram.body.RegistrationBody
import com.ithub.bigbrotherbackend.telegram.body.VerificationBody
import com.ithub.bigbrotherbackend.telegram.service.TelegramAuthService
import com.ithub.bigbrotherbackend.telegram.service.TelegramService
import com.ithub.bigbrotherbackend.util.leaveOnlyDigits
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

@Controller
class TelegramHandler(
    private val telegramService: TelegramService,
    private val telegramAuthService: TelegramAuthService
) : BaseHandler() {

    fun clientStatus() = handle(
        awaitRequest = { serverRequest ->
            object : BaseRequest(serverRequest) {
                val chat = serverRequest.pathVariable("chat").toInt()
            }
        },
        handler = { request ->
            ServerResponse
                .ok()
                .bodyValueAndAwait(telegramAuthService.clientStatus(chat = request.chat))
        }
    )

    fun registration() = handle(
        awaitRequest = { serverRequest ->
            val body: RegistrationBody = serverRequest.awaitBody()

            object : BaseRequest(serverRequest) {
                val chat = body.chat
                val phoneNumber: String = body.phoneNumber.trim().leaveOnlyDigits()
            }
        },
        handler = { request ->
            telegramAuthService.registration(
                chat = request.chat,
                phoneNumber = request.phoneNumber
            )

            ServerResponse
                .ok()
                .buildAndAwait()
        }
    )

    fun verification() = handle(
        awaitRequest = { serverRequest ->
            val body: VerificationBody = serverRequest.awaitBody()

            object : BaseRequest(serverRequest) {
                val chat: Int = body.chat
                val code: String = body.code.trim()
            }
        },
        handler = { request ->
            telegramAuthService.verification(
                chat = request.chat,
                code = request.code
            )

            ServerResponse
                .ok()
                .buildAndAwait()
        }
    )


    fun tryResendBufferedNotifications() = handle(
        awaitRequest = skipAwaitRequest(),
        handler = {
            telegramService.tryResendBufferedNotifications()
            ServerResponse
                .ok()
                .buildAndAwait()
        }
    )

}
