package com.ithub.bigbrotherbackend.student

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class StudentServiceImpl(
    private val studentRepository: StudentRepository
) : StudentService {

    override suspend fun queryAllStudents(
        limit: Long?,
        offset: Long?
    ): Flow<Student> {
        return studentRepository.queryAllBy(limit, offset)
    }


}
