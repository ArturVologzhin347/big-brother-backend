package com.ithub.bigbrotherbackend.telegram.service

import com.ithub.bigbrotherbackend.respondent.RespondentService
import com.ithub.bigbrotherbackend.security.code.RandomCodeGenerator
import com.ithub.bigbrotherbackend.telegram.model.ClientStatus
import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import com.ithub.bigbrotherbackend.telegram.model.TelegramToken
import com.ithub.bigbrotherbackend.telegram.repository.TelegramClientRepository
import com.ithub.bigbrotherbackend.util.apiError
import com.ithub.bigbrotherbackend.util.loggerFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class TelegramAuthService(
    private val respondentService: RespondentService,
    private val clientService: TelegramClientService,
    private val clientRepository: TelegramClientRepository,
    private val codeGenerator: RandomCodeGenerator,
    private val encoder: PasswordEncoder,
) {

    private val logger by loggerFactory()
    private val tokens = hashMapOf<Int, TelegramToken>()

    suspend fun clientStatus(chat: Int): ClientStatus {
        if(tokens.containsKey(chat)) {
            return ClientStatus.NOT_VERIFIED
        }

        return  if(clientService.existsBy(chat)) ClientStatus.REGISTERED else ClientStatus.UNREGISTERED
    }

    suspend fun registration(chat: Int, phoneNumber: String) {
        if (clientService.existsBy(chat)) {
            apiError(
                code = "CHAT_ALREADY_VERIFIED",
                message = "Chat already verified",
                status = HttpStatus.BAD_REQUEST
            )
        }

        val respondent = respondentService.findRespondentBy(phoneNumber) ?: apiError(
            code = "WRONG_PHONE_NUMBER",
            message = "Wrong phone number",
            status = HttpStatus.NOT_FOUND
        )

        val config = respondentService
            .findRespondentConfigBy(respondentId = respondent.id())
            .awaitSingle()

        val code = codeGenerator.generateCode()
        val secretCode = encoder.encode(code)

        val token = TelegramToken(
            configId = config.id(),
            chat = chat,
            code = secretCode,
            attempts = 0,
            timestamp = System.currentTimeMillis()
        )


        logger.debug("Chat: $chat, CODE: <$code>") // TODO send sms with code

        tokens[chat] = token
    }

    suspend fun verification(chat: Int, code: String) {
        if (!tokens.containsKey(chat)) {
            apiError(
                code = "TOKEN_NOT_CREATED",
                message = "Token for chat $chat not created",
                status = HttpStatus.NOT_FOUND
            )
        }

        val token = tokens.getValue(chat)
        val now = System.currentTimeMillis()
        val difference = now - token.timestamp

        logger.debug("Token lives ${TimeUnit.MILLISECONDS.toMinutes(difference)} minutes.")

        // Token too old
        if (difference > TELEGRAM_TOKEN_LIFETIME) {
            killToken(chat)
        }

        // Wrong code
        if (!encoder.matches(code, token.code)) {
            val currentAttempts = token.attempts + 1
            if (currentAttempts > MAX_ATTEMPTS) {
                killToken(chat, errorCode = "ATTEMPTS_ARE_OVER") // No more attempts, try to verify again
            }

            // Update attempts counts
            tokens[chat] = token.copy(attempts = currentAttempts)

            apiError(
                code = "WRONG_CODE",
                message = "Wrong code",
                status = HttpStatus.FORBIDDEN
            )
        }

        // Successful

        clientService
            .findAllClientsBy(respondentConfigId = token.configId)
            .onEach {
                // TODO Notify every telegram client about logging in
            }.collect()

        clientRepository.save(
            TelegramClient(
                chat = chat,
                configId = token.configId
            )
        )

        tokens.remove(chat)
    }

    private fun killToken(chat: Int, errorCode: String = "TOKEN_WAS_KILLED"): Nothing {
        tokens.remove(chat)
        apiError(
            code = errorCode,
            message = "Token is dead",
            status = HttpStatus.UNAUTHORIZED
        )
    }

    @Scheduled(fixedDelay = TOKEN_CLEANER_DELAY, timeUnit = TimeUnit.MINUTES)
    private fun tokenCleaner() {
        val now = System.currentTimeMillis()
        var count = 0

        tokens.forEach { (chat, token) ->
            if ((now - token.timestamp) > TELEGRAM_TOKEN_LIFETIME) {
                tokens.remove(chat)
                count++
            }
        }

        logger.info("Telegram tokens was cleaned: $count. Tokens now: ${tokens.size}")
    }

    companion object {
        private const val MAX_ATTEMPTS = 3
        private const val TOKEN_CLEANER_DELAY = 15L
        private val TELEGRAM_TOKEN_LIFETIME = TimeUnit.MINUTES.toMillis(15L)
    }

}
