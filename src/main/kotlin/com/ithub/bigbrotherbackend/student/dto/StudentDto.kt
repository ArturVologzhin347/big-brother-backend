package com.ithub.bigbrotherbackend.student.dto

import com.ithub.bigbrotherbackend.student.Student

data class StudentDto(
    val id: String,
    val name: String,
    val surname: String,
    val patronymic: String
) {

    companion object {
        fun from(student: Student) = StudentDto(
            id = student.id(),
            name = student.name,
            surname = student.surname,
            patronymic = student.patronymic
        )
    }

}
