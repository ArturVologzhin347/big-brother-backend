package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.base.hanlder.BaseHandler
import com.ithub.bigbrotherbackend.base.hanlder.BaseRequest
import com.ithub.bigbrotherbackend.skud.body.AcceptSkudEventBody
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.*

@Controller
class SkudHandler(
    private val skudService: SkudService
) : BaseHandler() {

    fun queryAll() = handle(
        awaitRequest = { serverRequest ->
            val studentId = serverRequest.queryParamOrNull("student")
            val limit = serverRequest.queryParamOrNull("limit")?.toInt()
            val offset = serverRequest.queryParamOrNull("offset")?.toInt()

            object : BaseRequest(serverRequest) {
                val studentId: String? = studentId
                val limit: Int = limit ?: 10
                val offset: Int = offset ?: 0
            }
        },
        handler = { request ->
            with(request) {
                ServerResponse
                    .ok()
                    .bodyValueAndAwait(
                        skudService.queryAllWithPagination(
                            studentId = studentId,
                            limit = limit,
                            offset = offset
                        )
                    )
            }
        }
    )

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
