package com.ithub.bigbrotherbackend.util

import com.ithub.bigbrotherbackend.error.model.ApiException
import org.springframework.http.HttpStatus


fun apiError(
    ex: Throwable,
    code: String = "INTERNAL_SERVER_ERROR",
    status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
): Nothing {
    throw ApiException(ex, code, status)
}

fun apiError(
    message: String,
    code: String = "INTERNAL_SERVER_ERROR",
    status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
): Nothing {
    throw ApiException(message, code, status)
}