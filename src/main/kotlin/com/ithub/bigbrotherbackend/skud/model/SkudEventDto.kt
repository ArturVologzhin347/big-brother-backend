package com.ithub.bigbrotherbackend.skud.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class SkudEventDto(

    @JsonProperty("id")
    val id: Long,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // TODO
    @JsonProperty("timestamp")
    val timestamp: LocalDateTime,

    @JsonProperty("type")
    val type: String,

    @JsonProperty("card_id")
    val cardId: Long? = null

)

fun SkudEvent.toDto() = SkudEventDto(
    id = id(),
    timestamp = timestamp,
    type = type.name,
    cardId = cardId
)
