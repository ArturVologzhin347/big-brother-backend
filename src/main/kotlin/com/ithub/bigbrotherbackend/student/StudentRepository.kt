package com.ithub.bigbrotherbackend.student

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : CoroutineCrudRepository<Student, String> {

}
