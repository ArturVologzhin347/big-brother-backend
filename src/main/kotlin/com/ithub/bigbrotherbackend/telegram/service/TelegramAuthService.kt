package com.ithub.bigbrotherbackend.telegram.service

import com.ithub.bigbrotherbackend.respondent.RespondentService
import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import com.ithub.bigbrotherbackend.telegram.repository.TelegramClientRepository

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


        
        TODO()

    }




}
