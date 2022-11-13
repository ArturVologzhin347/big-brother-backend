package com.ithub.bigbrotherbackend.telegram.model

import com.ithub.bigbrotherbackend.db.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "telegram_config")
data class TelegramConfig(

    @Id
    @Column("id")
    private val _id: Long? = null,

    @Column("telegram_client_id")
    val clientId: Long,

    @Column("skud_enabled")
    val skudEnabled: Boolean = true,

): Entity<Long> {
    override fun id() = checkNotNull(_id)
}