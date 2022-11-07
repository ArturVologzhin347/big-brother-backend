package com.ithub.bigbrotherbackend.telegram.handler

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

suspend fun createTelegramClient(req: ServerRequest): ServerResponse {
    TODO()
}

data class CreateTelegramUserBody(

    @JsonProperty("phoneNumber")
    val phoneNumber: String,

    @JsonProperty("chatId")
    val chatId: Long,

)
