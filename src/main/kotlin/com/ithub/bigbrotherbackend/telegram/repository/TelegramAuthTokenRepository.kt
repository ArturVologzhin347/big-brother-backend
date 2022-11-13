package com.ithub.bigbrotherbackend.telegram.repository

import com.ithub.bigbrotherbackend.telegram.model.TelegramAuthToken
import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface TelegramAuthTokenRepository : CoroutineCrudRepository<TelegramAuthToken, Long> {

    @Query("SELECT * FROM telegram_auth_token WHERE telegram_client_id=:clientId")
    fun findByClientId(clientId: Long): Mono<TelegramAuthToken>

}
