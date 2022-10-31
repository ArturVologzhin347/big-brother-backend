package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.event.RawEvent
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.*

@Controller
class SkudEventHandler(
    private val skudEventService: SkudEventService
) {

    suspend fun sendSkudEvent(req: ServerRequest): ServerResponse {
        val rawSkudEvent = req.awaitBody(RawEvent.RawSkudEvent::class)
        skudEventService.sendSkudEvent(rawSkudEvent)

        return ServerResponse
            .ok()
            .buildAndAwait()
    }

    suspend fun queryAllSkudEvents(req: ServerRequest): ServerResponse {
        val limit = req.queryParamOrNull("limit")?.toLong()
        val offset = req.queryParamOrNull("offset")?.toLong()

        val events = skudEventService.queryAllSkudEvents(limit, offset)
        return ServerResponse
            .ok()
            .bodyAndAwait(skudEventService.convertToDto(events))
    }

}
