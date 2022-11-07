package com.ithub.bigbrotherbackend.telegram.repository

import com.ithub.bigbrotherbackend.telegram.model.TelegramAuthToken
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TelegramAuthTokenRepository : CoroutineCrudRepository<TelegramAuthToken, Long> {

    @Query(
        """
            WITH auth_token AS (
            INSERT INTO telegram_auth_token (telegram_client_id, code) 
            VALUES (:telegramClientId, :code) 
            ON CONFLICT (telegram_client_id) 
            DO UPDATE SET (id, telegram_client_id, code, attempts, timestamp) = 
            (EXCLUDED.id, EXCLUDED.telegram_client_id, EXCLUDED.code, EXCLUDED.attempts, EXCLUDED.timestamp)
            RETURNING *
            )
            SELECT * FROM auth_token
        """
    )
    suspend fun create(telegramClientId: Long, code: String): TelegramAuthToken


    override suspend fun <S : TelegramAuthToken> save(entity: S): TelegramAuthToken {
        throw NotImplementedError()
    }

    override fun <S : TelegramAuthToken> saveAll(entities: Iterable<S>): Flow<S> {
        throw NotImplementedError()
    }

    override fun <S : TelegramAuthToken> saveAll(entityStream: Flow<S>): Flow<S> {
        throw NotImplementedError()
    }

}
