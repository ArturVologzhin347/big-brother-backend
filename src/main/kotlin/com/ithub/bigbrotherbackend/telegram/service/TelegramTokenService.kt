package com.ithub.bigbrotherbackend.telegram.service

import com.ithub.bigbrotherbackend.security.code.RandomCodeGenerator
import com.ithub.bigbrotherbackend.telegram.exception.TokenIsDeadException
import com.ithub.bigbrotherbackend.telegram.exception.WrongCodeException
import com.ithub.bigbrotherbackend.telegram.model.TelegramAuthToken
import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import com.ithub.bigbrotherbackend.telegram.repository.TelegramAuthTokenRepository
import com.ithub.bigbrotherbackend.telegram.repository.TelegramClientRepository
import com.ithub.bigbrotherbackend.util.logFactory
import com.ithub.bigbrotherbackend.util.toLong



import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
class TelegramTokenService(
    private val tokenRepository: TelegramAuthTokenRepository,
    private val clientRepository: TelegramClientRepository,
    private val codeGenerator: RandomCodeGenerator,
    private val encoder: PasswordEncoder
) {

    private val logger by logFactory()

    suspend fun startVerificationProtocol(client: TelegramClient) {

        if(client.verified) {
            logger.warn("Telegram client is already verified.")
            return
        }

        var token = tokenRepository.findByClientId(client.id()).awaitSingleOrNull()

        val code = codeGenerator.generateCode()
        val encryptedCode = encoder.encode(code)

        // Create new if it does not exist or update
        token = token?.copy(
            telegramClientId = client.id(),
            code = encryptedCode
        )
            ?: TelegramAuthToken(
                telegramClientId = client.id(),
                code = encryptedCode,
                attempts = 0,
                timestamp = LocalDateTime.now()
            )

        tokenRepository.save(token)

        logger.debug("SECRET CODE: <$code>")

        // TODO send non-encrypted code to respondent by SMS
    }


    @Throws(TokenIsDeadException::class, WrongCodeException::class)
    suspend fun verification(client: TelegramClient, code: String) {

        val token = tokenRepository.findByClientId(client.id()).awaitSingleOrNull()

        checkNotNull(token) {
          "Token should be created!"
        }

        val timestamp = token.timestamp.toLong()
        val now = LocalDateTime.now().toLong()
        val difference = now - timestamp

        logger.debug("Token lives ${TimeUnit.MILLISECONDS.toMinutes(difference)} minutes.")
        // Token too old
        if(difference > TELEGRAM_TOKEN_LIFETIME) {
            killToken(token)
        }

        // Wrong code
        if(!encoder.matches(code, token.code)) {
            val currentAttempts = token.attempts + 1
            if(currentAttempts > MAX_ATTEMPTS) {
                killToken(token) // No more attempts, try to verify again
            }

            tokenRepository.save(
                token.copy(
                    attempts = currentAttempts // If token not killed, attempts++
                )
            )

            throw WrongCodeException()
        }

        tokenRepository.deleteById(token.id())

        clientRepository.save(
            client.copy(
                verified = true // Client verified!
            )
        )

    }

    private suspend fun killToken(token: TelegramAuthToken): Nothing {
        val client = clientRepository.findById(token.id())

        if(client != null && !client.verified) {
            clientRepository.deleteById(client.id())
        }

        tokenRepository.deleteById(token.id())
        throw TokenIsDeadException()
    }

    companion object {
        private const val MAX_ATTEMPTS = 3
        private val TELEGRAM_TOKEN_LIFETIME = TimeUnit.MINUTES.toMillis(30L)

    }

}
