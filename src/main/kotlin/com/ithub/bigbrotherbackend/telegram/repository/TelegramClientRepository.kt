package com.ithub.bigbrotherbackend.telegram.repository

import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Mono

interface TelegramClientRepository : CoroutineCrudRepository<TelegramClient, Long> {

    @Query("SELECT * FROM telegram_client WHERE chat_id=:chatId")
    fun findByChatId(chatId: Long): Mono<TelegramClient?>

}
