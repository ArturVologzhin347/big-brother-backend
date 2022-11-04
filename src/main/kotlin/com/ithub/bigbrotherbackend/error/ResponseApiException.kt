package com.ithub.bigbrotherbackend.error

abstract class ResponseApiException(val status: Int, message: String) : ApiException(message)

