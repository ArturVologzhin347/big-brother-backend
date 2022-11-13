package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.base.hanlder.BaseHandler
import com.ithub.bigbrotherbackend.base.hanlder.BaseRequest
import com.ithub.bigbrotherbackend.skud.body.AcceptSkudEventBody
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Controller
class SkudHandler(
    private val skudService: SkudService
) : BaseHandler() {

    fun acceptSkudEvent() = handle(
        awaitRequest = { serverRequest ->
            val body = serverRequest.awaitBody(AcceptSkudEventBody::class)

            object : BaseRequest(serverRequest) {
                val cardNumber = body.cardNumber
                val type = SkudEvent.Type.valueOf(body.type)
                val timestamp = body.timestamp
            }
        },
        handler = { request ->

            with(request) {
                skudService.acceptSkudEvent(
                    cardNumber = cardNumber,
                    type = type,
                    timestamp = timestamp
                )
            }

            ServerResponse
                .ok()
                .bodyValueAndAwait("OK")
        }
    )

}
