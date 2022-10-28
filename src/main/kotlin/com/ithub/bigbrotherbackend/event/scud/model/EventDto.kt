package com.ithub.bigbrotherbackend.event.scud.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class EventDto(
    val id: Long,
    val type: String,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // TODO
    val timestamp: LocalDateTime,

    @JsonProperty("card_id")
    val cardId: Long
)

fun Event.toDto() = EventDto(
    id = id(),
    type = type.name,
    timestamp = timestamp,
    cardId = cardId
)
