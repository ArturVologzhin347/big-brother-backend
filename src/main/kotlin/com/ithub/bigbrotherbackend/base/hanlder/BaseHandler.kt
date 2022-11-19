package com.ithub.bigbrotherbackend.base.hanlder

import com.ithub.bigbrotherbackend.error.model.ApiException
import com.ithub.bigbrotherbackend.error.model.BadRequestApiException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

typealias HandlerFunction = suspend (request: ServerRequest) -> ServerResponse

abstract class BaseHandler {

    protected fun <R : BaseRequest> handle(
        awaitRequest: (suspend (serverRequest: ServerRequest) -> R),
        handler: suspend (request: R) -> ServerResponse
    ): HandlerFunction {
        return { serverRequest ->
            val request: R = try {
                awaitRequest.invoke(serverRequest)
            } catch (e: ApiException) {
                throw e
            } catch (e: Exception) {
                throw BadRequestApiException(e.message ?: "no message")
            }

            handler.invoke(request)
        }
    }

    protected fun skipAwaitRequest(): (serverRequest: ServerRequest) -> BaseRequest = { object : BaseRequest(it) {} }

}
