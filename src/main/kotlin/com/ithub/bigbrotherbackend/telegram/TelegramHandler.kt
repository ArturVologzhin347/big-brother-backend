package com.ithub.bigbrotherbackend.telegram

import com.ithub.bigbrotherbackend.telegram.body.RegistrationTelegramUserBody
import com.ithub.bigbrotherbackend.telegram.body.VerifyTelegramClientBody
import com.ithub.bigbrotherbackend.telegram.exception.*
import com.ithub.bigbrotherbackend.telegram.repository.TelegramClientRepository
import org.springframework.stereotype.Controller
import com.ithub.bigbrotherbackend.telegram.service.TelegramAuthService
import com.ithub.bigbrotherbackend.telegram.service.TelegramTokenService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.reactive.function.server.*

/*

TODO REFACTOR

 */

@Controller
class TelegramHandler(
    private val authService: TelegramAuthService,
    private val tokenService: TelegramTokenService,
    private val clientRepository: TelegramClientRepository
) {

    suspend fun registration(req: ServerRequest): ServerResponse {
        val body: RegistrationTelegramUserBody = req.awaitBody()

        try {
            authService.registration(body.phoneNumber, body.chat)
        } catch (e: PhoneNumberInvalidException) {
            return ServerResponse
                .badRequest()
                .bodyValueAndAwait("INVALID_PHONE_NUMBER")
        } catch (e: WrongPhoneNumberException) {
            return ServerResponse
                .badRequest()
                .bodyValueAndAwait("WRONG_PHONE_NUMBER")
        } catch (e: AlreadyClientCreatedException) {
            return ServerResponse
                .badRequest()
                .bodyValueAndAwait("CLIENT_ALREADY_CREATED")
        }

        // TODO
        val secretPhoneNumber = body.phoneNumber.replaceRange(
            0,
            body.phoneNumber.length - 4,
            "*****"
        )

        return ServerResponse
            .ok()
            .bodyValueAndAwait(
                mapOf(
                    "phoneNumber" to secretPhoneNumber
                )

            )
    }

    suspend fun verification(req: ServerRequest): ServerResponse {
        val body: VerifyTelegramClientBody = req.awaitBody()

        try {
            val client = clientRepository.findByChatId(body.chat).awaitSingle()
            tokenService.verification(client, body.code.trim())
        } catch (e: WrongCodeException) {
            return ServerResponse
                .badRequest()
                .bodyValueAndAwait("WRONG_CODE")
        } catch (e: TokenIsDeadException) {
            return ServerResponse
                .badRequest()
                .bodyValueAndAwait("TOKEN_IS_DEAD")
        }

        return ServerResponse
            .ok()
            .buildAndAwait()
    }

}
