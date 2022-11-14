package com.ithub.bigbrotherbackend.student

import com.ithub.bigbrotherbackend.skud.SkudService
import com.ithub.bigbrotherbackend.skud.dto.SkudEventDto
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.student.dto.StudentDisplayDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val skudService: SkudService,
    private val studentRepository: StudentRepository,
) {

    // TODO cache
    suspend fun queryAll(): Flow<StudentDisplayDto> {
        return studentRepository
            .findAll()
            .map { student ->
                val event = skudService.findLast(student.id()).awaitSingleOrNull()

                val status = if (event == null) {
                    StudentDisplayDto.Status.OUT
                } else {
                    when (event.type) {
                        SkudEvent.Type.ENTER -> StudentDisplayDto.Status.IN
                        SkudEvent.Type.EXIT -> StudentDisplayDto.Status.OUT
                    }
                }

                StudentDisplayDto.from(
                    student = student,
                    event = event?.let { SkudEventDto.from(it) },
                    status = status
                )
            }
    }
}
