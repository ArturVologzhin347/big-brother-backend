package com.ithub.bigbrotherbackend.telegram.service

import com.ithub.bigbrotherbackend.security.code.RandomCodeGenerator
import com.ithub.bigbrotherbackend.telegram.model.TelegramAuthToken
import com.ithub.bigbrotherbackend.telegram.repository.TelegramAuthTokenRepository
import com.ithub.bigbrotherbackend.util.logFactory
import org.springframework.stereotype.Service

@Service
class TelegramTokenService(
    private val authTokenRepository: TelegramAuthTokenRepository,
    private val codeGenerator: RandomCodeGenerator
) {

    private val logger by logFactory()

    suspend fun createAuthToken(
        clientId: Long,
        phoneNumber: String
    ): TelegramAuthToken {

        val token = authTokenRepository.create(
            telegramClientId = clientId,
            code = codeGenerator.generateCode()
        )

        logger.error(token.code)
        println(token)

        // TODO send sms with code by phoneNumber

        return token

    }

}
