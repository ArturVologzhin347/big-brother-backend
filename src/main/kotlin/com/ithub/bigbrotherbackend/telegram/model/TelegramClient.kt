package com.ithub.bigbrotherbackend.telegram.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "telegram_client")
data class TelegramClient(

    @Id
    @Column("id")
    private val _id: Long? = null,

    @Column("chat_id")
    val chatId: Long,

    @Column("verified")
    val verified: Boolean = false,

    @Column("respondent_id")
    val respondentId: Long

) {

    fun id() = checkNotNull(_id)

}
