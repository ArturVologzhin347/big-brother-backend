package com.ithub.bigbrotherbackend.telegram.repository

import com.ithub.bigbrotherbackend.notification.core.BaseNotificationConfigRepository
import com.ithub.bigbrotherbackend.telegram.model.TelegramConfig
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface TelegramConifgRepository : CoroutineCrudRepository<TelegramConfig, Long>,
    BaseNotificationConfigRepository<TelegramConfig, Long> {

    @Query("SELECT * FROM telegram_config WHERE telegram_client_id=:id")
    override fun findByClientId(id: Long): Mono<TelegramConfig>

}