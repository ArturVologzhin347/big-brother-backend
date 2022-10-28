package com.ithub.bigbrotherbackend.event.scud.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "event")
data class Event(

    @Id
    @Column("id")
    private val _id: Long? = null,

    @Column("type")
    val type: Type = Type.ENTER,

    @Column("timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @Column("card_id")
    val cardId: Long

) {

    fun id() = checkNotNull(_id)

    enum class Type {
        ENTER,
        EXIT
    }

}
