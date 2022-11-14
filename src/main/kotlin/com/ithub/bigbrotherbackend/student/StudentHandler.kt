package com.ithub.bigbrotherbackend.student

import com.ithub.bigbrotherbackend.base.hanlder.BaseHandler
import com.ithub.bigbrotherbackend.base.hanlder.BaseRequest
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait

@Controller
class StudentHandler(
    private val studentService: StudentService
) : BaseHandler() {

    fun queryAll() = handle(
        awaitRequest = { serverRequest -> object : BaseRequest(serverRequest) {} },
        handler = {
            ServerResponse
                .ok()
                .bodyAndAwait(studentService.queryAll())
        }
    )

}
