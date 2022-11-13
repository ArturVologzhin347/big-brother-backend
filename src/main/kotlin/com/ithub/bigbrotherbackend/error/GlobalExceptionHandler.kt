package com.ithub.bigbrotherbackend.error

import com.fasterxml.jackson.databind.ObjectMapper
import com.ithub.bigbrotherbackend.Profiles
import com.ithub.bigbrotherbackend.error.dto.GlobalApiException
import com.ithub.bigbrotherbackend.error.model.ApiException
import com.ithub.bigbrotherbackend.util.loggerFactory
import com.ithub.bigbrotherbackend.util.profileIsActive
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.core.env.Environment
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
@Order(-2)
class GlobalExceptionHandler(
    private val env: Environment,
    private val objectMapper: ObjectMapper
) : ErrorWebExceptionHandler {

    private val logger by loggerFactory()

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val response = exchange.response.apply {
            headers.contentType = MediaType.APPLICATION_JSON
        }

        val bufferFactory: DataBufferFactory = response.bufferFactory()

        val exception = when (ex) {
            is ApiException -> ex.toGlobalException(env)
            else -> ApiException(ex).toGlobalException(env)
        }

        if (exception.httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
            logger.error(exception.pretty())
            logger.error(exception.trace)
        } else {
            logger.warn(exception.pretty())
        }

        response.statusCode = exception.httpStatus
        val dataBuffer: DataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(exception))
        return exchange.response.writeWith(Mono.just(dataBuffer))
    }

    private fun ApiException.toGlobalException(env: Environment) = GlobalApiException(
        code = code,
        message = message ?: "no message",
        httpStatus = status,
        trace = if (env.profileIsActive(Profiles.DEV)) stackTraceToString() else null
    )
}
