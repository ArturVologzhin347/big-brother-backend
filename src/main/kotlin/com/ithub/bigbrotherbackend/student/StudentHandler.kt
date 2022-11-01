package com.ithub.bigbrotherbackend.student

import com.ithub.bigbrotherbackend.util.getPaginationParams
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait

@Controller
class StudentHandler(
    private val studentService: StudentService
) {

    suspend fun queryAllStudents(req: ServerRequest): ServerResponse {
        val limitAndOffset = req.getPaginationParams()

        return ServerResponse
            .ok()
            .bodyAndAwait(studentService.queryAllStudents(
                limit = limitAndOffset.first,
                offset = limitAndOffset.second
            ))
    }

}
