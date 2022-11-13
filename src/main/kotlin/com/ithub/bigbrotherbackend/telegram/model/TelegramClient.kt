package com.ithub.bigbrotherbackend.telegram.model

import com.ithub.bigbrotherbackend.db.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "telegram_client")
data class TelegramClient(

    @Id
    @Column("id")
    private val _id: Long? = null,

    @Column("chat")
    val chat: Long,

    @Column("verified")
    val verified: Boolean = false,

    @Column("respondent_id")
    val respondentId: Long

) : Entity<Long> {

    override fun id() = checkNotNull(_id)

}
