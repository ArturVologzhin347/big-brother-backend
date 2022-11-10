package com.ithub.bigbrotherbackend.telegram.repository

import com.ithub.bigbrotherbackend.telegram.model.TelegramAuthToken
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TelegramAuthTokenRepository : CoroutineCrudRepository<TelegramAuthToken, Long> {


}
