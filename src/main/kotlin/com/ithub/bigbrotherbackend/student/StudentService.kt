package com.ithub.bigbrotherbackend.student

import com.ithub.bigbrotherbackend.student.model.Student
import kotlinx.coroutines.flow.Flow

interface StudentService {

    suspend fun queryAllStudents(limit: Long?, offset: Long?): Flow<Student>

}
