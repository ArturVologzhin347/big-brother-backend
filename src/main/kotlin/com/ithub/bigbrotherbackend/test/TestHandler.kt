package com.ithub.bigbrotherbackend.test

import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

@Controller
class TestHandler {


    suspend fun test(request: ServerRequest): ServerResponse {
        return ServerResponse.badRequest().buildAndAwait()
    }

}