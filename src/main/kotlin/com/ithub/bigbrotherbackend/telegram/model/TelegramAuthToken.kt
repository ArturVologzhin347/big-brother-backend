package com.ithub.bigbrotherbackend.telegram.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "telegram_auth_token")
data class TelegramAuthToken(

    @Id
    @Column("id")
    private val _id: Long? = null,

    @Column("telegram_client_id")
    val telegramClientId: Long,

    @Column("code")
    val code: String,

    @Column("attempts")
    val attempts: Int = 0,

    @Column("timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now()

) {

    fun id() = checkNotNull(_id)

}
