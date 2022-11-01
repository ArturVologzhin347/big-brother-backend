package com.ithub.bigbrotherbackend.util

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.queryParamOrNull

fun ServerRequest.getPaginationParams(): Pair<Long?, Long?> {
    return queryParamOrNull("limit")?.toLong() to queryParamOrNull("offset")?.toLong()
}
