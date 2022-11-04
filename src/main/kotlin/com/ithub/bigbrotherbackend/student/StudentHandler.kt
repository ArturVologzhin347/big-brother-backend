package com.ithub.bigbrotherbackend.student

import org.springframework.stereotype.Controller

@Controller
class StudentHandler(
    private val studentService: StudentService
) {

//    suspend fun queryAllStudents(req: ServerRequest): ServerResponse {
//        val limitAndOffset = req.getPaginationParams()
//
//        return ServerResponse
//            .ok()
//            .bodyAndAwait(studentService.queryAllStudents(
//                limit = limitAndOffset.first,
//                offset = limitAndOffset.second
//            ))
//    }

}
