package com.ithub.bigbrotherbackend.student

import kotlinx.coroutines.flow.Flow

interface StudentService {

    suspend fun queryAllStudents(limit: Long?, offset: Long?): Flow<Student>

}
