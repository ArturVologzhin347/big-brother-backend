package com.ithub.bigbrotherbackend.error.handle

import com.ithub.bigbrotherbackend.error.ApiException
import com.ithub.bigbrotherbackend.error.ResponseApiException
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

//@Component
//@Order(-2)
//class GlobalExceptionInterceptor(
//
//) : ErrorWebExceptionHandler {
//
//
//    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
//        val bufferFactory: DataBufferFactory = exchange.response.bufferFactory()
//
//        when(ex) {
//            is ResponseApiException -> {}
//            is ApiException -> {}
//            else -> {}
//        }
//
//
//
//
//    }
//
//}