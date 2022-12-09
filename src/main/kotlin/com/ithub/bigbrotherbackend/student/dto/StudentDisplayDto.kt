package com.ithub.bigbrotherbackend.student.dto

import com.ithub.bigbrotherbackend.skud.dto.SkudEventDto
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.student.Student

class StudentDisplayDto(
    val id: String,
    val name: String,
    val surname: String,
    val patronymic: String,
    val lastEvent: SkudEventDto?,
    val status: Status
) {

    enum class Status {
        IN,
        OUT
    }

    companion object {
        fun from(student: Student, event: SkudEventDto?, status: Status) = StudentDisplayDto(
            id = student.id(),
            name = student.name,
            surname = student.surname,
            patronymic = student.patronymic,
            lastEvent = event,
            status = status
        )
    }

}
