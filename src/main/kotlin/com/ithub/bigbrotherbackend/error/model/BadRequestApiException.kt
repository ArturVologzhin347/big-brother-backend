package com.ithub.bigbrotherbackend.error.model

import org.springframework.http.HttpStatus

class BadRequestApiException(message: String) : ApiException(message, "BAD_REQUEST", HttpStatus.BAD_REQUEST)
