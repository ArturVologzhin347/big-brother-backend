package com.ithub.bigbrotherbackend.error

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
data class GlobalApiException(
    val code: String,
    val message: String,
    val status: Int,
    val trace: String?
)
