package com.ithub.bigbrotherbackend.student.model

import com.fasterxml.jackson.annotation.JsonProperty

data class StudentDto(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("surname")
    val surname: String,

    @JsonProperty("patronymic")
    val patronymic: String

)

fun Student.toDto() = StudentDto(
    id = id,
    name = name,
    surname = surname,
    patronymic = patronymic
)