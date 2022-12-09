package com.ithub.bigbrotherbackend.skud.dto

import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.student.dto.StudentDto
import com.ithub.bigbrotherbackend.util.toLong

data class SkudEventDisplayDto(
    val id: Long,
    val student: StudentDto,
    val type: SkudEvent.Type,
    val timestamp: Long
) {
    companion object {
        fun from(event: SkudEvent, student: StudentDto) = SkudEventDisplayDto(
            id = event.id(),
            student = student,
            type = event.type,
            timestamp = event.timestamp.toLong()
        )
    }
}
