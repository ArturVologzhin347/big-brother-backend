package com.ithub.bigbrotherbackend.telegram.model

import com.ithub.bigbrotherbackend.db.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "telegram_notification")
data class TelegramNotification(

    @Id
    @Column("id")
    private val _id: Long? = null,

    @Column("type")
    val type: Type,

    @Column("payload")
    val payload: String,

    @Column("telegram_client_id")
    val telegramClientId: Long

) : Entity<Long> {

    override fun id() = checkNotNull(_id)

    enum class Type {
        SKUD_EVENT,

    }

}
