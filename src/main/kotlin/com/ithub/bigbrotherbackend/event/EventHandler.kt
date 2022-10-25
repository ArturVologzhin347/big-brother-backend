package com.ithub.bigbrotherbackend.event

import com.ithub.bigbrotherbackend.util.toKtNullable
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class EventHandler(
    private val eventService: EventService
) {

    suspend fun queryAllBy(request: ServerRequest): ServerResponse {
        return try {
            val limit = request.queryParam("limit").toKtNullable()?.toInt()
            val offset = request.queryParam("offset").toKtNullable()?.toInt()

            ServerResponse
                .ok()
                .bodyAndAwait(eventService.queryAllBy(limit, offset))

        } catch (e: Exception) {

            ServerResponse
                .badRequest()
                .bodyValueAndAwait(e.localizedMessage)
        }

    }

}
