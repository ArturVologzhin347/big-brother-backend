package com.ithub.bigbrotherbackend.telegram.service

import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import com.ithub.bigbrotherbackend.telegram.repository.TelegramClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class TelegramClientService(
    private val telegramClientRepository: TelegramClientRepository
) {

    suspend fun existsBy(chat: Int): Boolean {
        return telegramClientRepository.existsBy(chat).awaitSingle()
    }

    suspend fun findClientBy(id: Long): TelegramClient? {
        return telegramClientRepository.findById(id)
    }

    fun findAllClientsBy(respondentConfigId: Long): Flow<TelegramClient> {
        return telegramClientRepository.findAllByConfigId(respondentConfigId)
    }

}
