package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.skud.model.RawSkudEvent
import com.ithub.bigbrotherbackend.util.queryPageRequest
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.*

@Controller
class SkudEventHandler(
    private val skudEventService: SkudEventService
) {

    suspend fun findAll(req: ServerRequest): ServerResponse {
        val pageRequest = req.queryPageRequest(defaultProperties = arrayOf("timestamp"))

        return ServerResponse
            .ok()
            .bodyValueAndAwait(skudEventService.findAll(pageRequest))
    }

    suspend fun handleSkudEvent(req: ServerRequest): ServerResponse {
        val rawSkudEvent = req.awaitBody(RawSkudEvent::class)
        skudEventService.handleEvent(rawSkudEvent)
        return ServerResponse
            .ok()
            .bodyValueAndAwait("OK")
    }

}
