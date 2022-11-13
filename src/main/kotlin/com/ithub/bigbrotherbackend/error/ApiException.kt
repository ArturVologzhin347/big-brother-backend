package com.ithub.bigbrotherbackend.error

abstract class ApiException(
    val code: String,
    message: String,
) : Exception(message)
