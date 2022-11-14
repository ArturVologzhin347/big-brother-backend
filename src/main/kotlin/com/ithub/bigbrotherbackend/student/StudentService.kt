package com.ithub.bigbrotherbackend.student

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {

    suspend fun findByCardId(id: Long): Student? {
        return studentRepository.findByCardId(id).awaitSingleOrNull()
    }

    suspend fun findById(id: String): Student? {
        return studentRepository.findById(id)
    }

}
