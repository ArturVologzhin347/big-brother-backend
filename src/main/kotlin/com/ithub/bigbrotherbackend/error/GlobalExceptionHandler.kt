package com.ithub.bigbrotherbackend.error

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.MediaType
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Configuration
@Order(-2)
class GlobalExceptionHandler(
    private val objectMapper: ObjectMapper
) : ErrorWebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val bufferFactory: DataBufferFactory = exchange.response.bufferFactory()

        when(ex) {
            is ApiException -> {}
            is ResponseStatusException -> {}
        }


        val exception = GlobalApiException(
            code = "ERROR_CODE",
            status = 404,
            message = "Bad Request",
            trace = ex.stackTraceToString()
        )


        exchange.response.headers.contentType = MediaType.APPLICATION_JSON
        val dataBuffer: DataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(exception))
        return exchange.response.writeWith(Mono.just(dataBuffer))
    }
}