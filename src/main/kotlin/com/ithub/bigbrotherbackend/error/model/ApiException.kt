package com.ithub.bigbrotherbackend.error.model

import org.springframework.http.HttpStatus

open class ApiException : Exception {
    val code: String
    val status: HttpStatus

    constructor(
        message: String,
        code: String,
        status: HttpStatus
    ) : super(message) {
        this.code = code
        this.status = status
    }

    constructor(
        ex: Throwable,
        code: String = "INTERNAL_SERVER_ERROR",
        status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    ) : super(ex) {
        this.code = code
        this.status = status
    }

}
