package com.ithub.bigbrotherbackend.telegram.repository

import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface TelegramClientRepository : CoroutineCrudRepository<TelegramClient, Long> {

    @Query("SELECT * FROM telegram_client WHERE respondent_config_id=:?")
    fun findAllByConfigId(respondentConfigId: Long): Flow<TelegramClient>

    @Query("SELECT EXISTS(SELECT 1 from telegram_client WHERE chat=:?)")
    fun existsBy(chat: Int): Mono<Boolean>

}
