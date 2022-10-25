package com.ithub.bigbrotherbackend.crud

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class CkudHandler(
    private val skudService: SkudService
) {

    suspend fun handleSkudEvent(request: ServerRequest): ServerResponse {
        val skudEvent = request.awaitBodyOrNull<SkudEvent>() ?: return ServerResponse
            .badRequest()
            .bodyValueAndAwait("") // TODO

        skudService.handleCrudRequest(skudEvent)
        return ServerResponse
            .ok()
            .buildAndAwait()
    }

}
