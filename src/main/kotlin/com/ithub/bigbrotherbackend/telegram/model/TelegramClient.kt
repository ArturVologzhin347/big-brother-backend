package com.ithub.bigbrotherbackend.telegram.model

import com.ithub.bigbrotherbackend.base.entity.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "telegram_client")
data class TelegramClient(

    @Id
    @Column("id")
    private val _id: Long? = null,

    @Column("chat")
    val chat: Int,

    @Column("respondent_config_id")
    val configId: Long

): Entity<Long>() {

    override fun id() = checkNotNull(_id)

}
