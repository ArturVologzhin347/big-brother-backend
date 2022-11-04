package com.ithub.bigbrotherbackend.util

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.queryParamOrNull


fun ServerRequest.queryPageRequest(
    defaultDirection: Sort.Direction = Sort.Direction.ASC,
    vararg defaultProperties: String
): PageRequest {
    val page = queryParamOrNull("page")?.toInt()
    val size = queryParamOrNull("size")?.toInt()

    check(page != null && size != null) {
        "" // TODO
    }

    val direction = queryParamOrNull("direction")?.let { Sort.Direction.valueOf(it) }
    val properties = queryParamOrNull("properties")?.trim()?.split(',')?.toTypedArray()

    val sort = Sort.by(
        direction ?: defaultDirection,
        *(properties ?: defaultProperties)
    )

    return PageRequest.of(page, size, sort)
}
