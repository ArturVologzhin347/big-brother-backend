package com.ithub.bigbrotherbackend.telegram

import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import com.ithub.bigbrotherbackend.telegram.handler.createTelegramClient
import com.ithub.bigbrotherbackend.telegram.handler.verifyTelegramClient

@Controller
class TelegramHandler {

    suspend fun proxyCreateClient(req: ServerRequest): ServerResponse = createTelegramClient(
        req = req
    )

    suspend fun proxyVerifyClient(req: ServerRequest): ServerResponse = verifyTelegramClient(
        req = req
    )

}
