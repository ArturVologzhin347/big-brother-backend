package com.ithub.bigbrotherbackend.skud.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.ithub.bigbrotherbackend.student.model.StudentDto
import com.ithub.bigbrotherbackend.util.toLong

data class SkudEventDto(

    @JsonProperty("id")
    val id: Long,

    @JsonProperty("timestamp")
    val timestamp: Long,

    @JsonProperty("type")
    val type: String,

    @JsonProperty("cardId")
    val cardId: Long,

    @JsonProperty("student")
    val student: StudentDto? = null

)

fun SkudEvent.toDto(student: StudentDto? = null) = SkudEventDto(
    id = id(),
    timestamp = timestamp.toLong(),
    type = type.name,
    cardId = cardId,
    student = student
)
