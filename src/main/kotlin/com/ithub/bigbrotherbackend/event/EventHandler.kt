package com.ithub.bigbrotherbackend.event

import com.ithub.bigbrotherbackend.event.scud.model.RawEvent
import com.ithub.bigbrotherbackend.event.scud.model.toDto
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.*

@Controller
class EventHandler(
    private val eventService: EventService
) {

    suspend fun reportSkudEvent(req: ServerRequest): ServerResponse {
        val rawEvent = req.awaitBody(RawEvent::class)
        eventService.reportSkudEvent(rawEvent)

        return ServerResponse
            .ok()
            .buildAndAwait()
    }

    suspend fun queryAllBy(req: ServerRequest): ServerResponse {
        val limit = req.queryParamOrNull("limit")?.toLong()
        val offset = req.queryParamOrNull("offset")?.toLong()

        return ServerResponse
            .ok()
            .bodyAndAwait(eventService.queryAllBy(limit, offset).map { it.toDto() })
    }

}
