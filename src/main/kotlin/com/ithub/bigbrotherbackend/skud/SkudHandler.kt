package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.base.hanlder.BaseHandler
import com.ithub.bigbrotherbackend.base.hanlder.BaseRequest
import com.ithub.bigbrotherbackend.skud.body.AcceptSkudEventBody
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.util.await
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.*

@Controller
class SkudHandler(
    private val skudService: SkudService
) : BaseHandler() {

    fun queryLast() = handle(
        awaitRequest = { serverRequest ->
            val timestamp = serverRequest.pathVariable("timestamp").toLong()
            val studentId = serverRequest.queryParamOrNull("student")
            object : BaseRequest(serverRequest) {
                val timestamp = timestamp
                val studentId = studentId
            }
        },
        handler = { request ->
            ServerResponse
                .ok()
                .bodyAndAwait(skudService.findLastAfter(request.studentId, request.timestamp))
        }
    )

    fun queryAll() = handle(
        awaitRequest = { serverRequest ->
            val studentId = serverRequest.queryParamOrNull("student")
            val limit = serverRequest.queryParamOrNull("limit")?.toInt()
            val older = serverRequest.queryParamOrNull("older")?.toLong()

            object : BaseRequest(serverRequest) {
                val studentId: String? = studentId
                val limit: Int = limit ?: 10
                val older: Long = older ?: System.currentTimeMillis()
            }
        },
        handler = { request ->
            with(request) {
                val events = skudService.queryAllWithPagination(
                    studentId,
                    limit,
                    older
                ).await()
                ServerResponse
                    .ok()
                    .bodyValueAndAwait(
                        mapOf(
                            "content" to events,
                            "olderEventTimestamp" to skudService.findMostOlderTimestamp(studentId)
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
