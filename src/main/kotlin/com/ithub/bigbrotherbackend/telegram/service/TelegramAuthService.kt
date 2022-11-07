package com.ithub.bigbrotherbackend.telegram.service

import com.ithub.bigbrotherbackend.respondent.RespondentService
import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import com.ithub.bigbrotherbackend.telegram.repository.TelegramClientRepository
import kotlinx.coroutines.reactor.awaitSingle

import org.springframework.stereotype.Service

@Service
class TelegramAuthService(
    private val clientRepository: TelegramClientRepository,
    private val tokenService: TelegramTokenService,
    private val respondentService: RespondentService,


    ) {

    suspend fun createTelegramClient(
        phoneNumber: String,
        chatId: Long
    ): TelegramClient {

        val respondent = respondentService.findRespondentByPhoneNumber(phoneNumber)

        val telegramClient = clientRepository.save(
            TelegramClient(
                chatId = chatId,
                respondentId = respondent.id()
            )
        )

        tokenService.createAuthToken(
            clientId = telegramClient.id(),
            phoneNumber = respondent.phoneNumber
        )








        TODO()

    }




}
