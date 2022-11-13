package com.ithub.bigbrotherbackend.error.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import org.springframework.http.HttpStatus

@JsonInclude(Include.NON_NULL)
class GlobalApiException(
    val code: String,
    val message: String,
    @JsonIgnore val httpStatus: HttpStatus,
    val trace: String?
) {

    val status: Int = httpStatus.value()

    fun pretty(): String {
        return "ERROR - $status; Code: $code; Message: $message"
    }

}
