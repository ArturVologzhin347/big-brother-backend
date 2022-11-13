package com.ithub.bigbrotherbackend.telegram.service

import com.ithub.bigbrotherbackend.respondent.RespondentService
import com.ithub.bigbrotherbackend.respondent.exception.RespondentWithNumberDoesntExists
import com.ithub.bigbrotherbackend.telegram.exception.AlreadyClientCreatedException
import com.ithub.bigbrotherbackend.telegram.exception.PhoneNumberInvalidException
import com.ithub.bigbrotherbackend.telegram.exception.WrongPhoneNumberException
import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import com.ithub.bigbrotherbackend.telegram.model.TelegramConfig
import com.ithub.bigbrotherbackend.telegram.repository.TelegramClientRepository
import com.ithub.bigbrotherbackend.telegram.repository.TelegramConifgRepository
import com.ithub.bigbrotherbackend.util.isPhoneNumber
import com.ithub.bigbrotherbackend.util.leaveOnlyDigits
import com.ithub.bigbrotherbackend.util.logFactory
import kotlinx.coroutines.reactor.awaitSingleOrNull

import org.springframework.stereotype.Service

@Service
class TelegramAuthService(
    private val clientRepository: TelegramClientRepository,
    private val configRepository: TelegramConifgRepository,
    private val tokenService: TelegramTokenService,
    private val respondentService: RespondentService
) {

    private val logger by logFactory()

    @Throws(
        WrongPhoneNumberException::class,
        PhoneNumberInvalidException::class,
        AlreadyClientCreatedException::class
    )
    suspend fun registration(phoneNumber: String, chat: Long): TelegramClient {

        //TODO
//        if (!phoneNumber.isPhoneNumber()) {
//            throw PhoneNumberInvalidException(phoneNumber)
//        }

        val formattedPhone = phoneNumber.leaveOnlyDigits()

        val respondent = try {
            respondentService.findByPhoneNumber(formattedPhone)
        } catch (e: RespondentWithNumberDoesntExists) {
            throw WrongPhoneNumberException(formattedPhone)
        }

        var client = clientRepository.findByRespondentId(respondent.id()).awaitSingleOrNull()

        client = clientRepository.save(
            if (client != null) { // Respondent already associated with chat

                if (client.chat == chat) { // Client already exists

                    if (!client.verified) { // Client not verified
                        tokenService.startVerificationProtocol(client)
                    }

                    throw AlreadyClientCreatedException(client.chat, client.respondentId)
                } else {
                    // Same respondent, another chat
                    // TODO notify client.chat about logout
                }

                client.copy( // Another chat, update current client
                    chat = chat,
                    verified = false
                )

            } else { // Telegram client not created yet
                TelegramClient(
                    chat = chat,
                    respondentId = respondent.id()
                )
            }
        )

        configRepository.save(
            TelegramConfig(
                clientId = client.id()
            )
        )

        tokenService.startVerificationProtocol(client)

        return client
    }

}
