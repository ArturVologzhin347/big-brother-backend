package com.ithub.bigbrotherbackend.notification.core

import reactor.core.publisher.Mono

interface BaseNotificationClientRepository<T> {

    fun findByRespondentId(id: Long): Mono<T>

}