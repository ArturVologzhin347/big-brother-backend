package com.ithub.bigbrotherbackend.student

import com.ithub.bigbrotherbackend.skud.SkudService
import com.ithub.bigbrotherbackend.skud.dto.SkudEventDto
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.student.dto.StudentDisplayDto
import com.ithub.bigbrotherbackend.student.dto.StudentDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val skudService: SkudService,
    private val studentRepository: StudentRepository,
) {

    suspend fun queryAll(): Flow<StudentDto> {
        return studentRepository
            .findAll()
            .map { StudentDto.from(it) }
    }

    suspend fun queryDisplayedAll(): Flow<StudentDisplayDto> {
        return studentRepository
            .findAll()
            .map { student -> queryDisplayedOne(student.id()) }
    }

    suspend fun queryDisplayedOne(id: String): StudentDisplayDto {
        val student = studentRepository.findById(id)
            ?: throw NullPointerException("Student with id $id not found.")

        val event = skudService
            .findLastBy(id)
            .awaitSingleOrNull()

        val status = if (event == null) {
            StudentDisplayDto.Status.OUT
        } else {
            when (event.type) {
                SkudEvent.Type.ENTER -> StudentDisplayDto.Status.IN
                SkudEvent.Type.EXIT -> StudentDisplayDto.Status.OUT
            }
        }

        return StudentDisplayDto.from(
            student = student,
            event = event?.let { SkudEventDto.from(it) },
            status = status
        )
    }

}
