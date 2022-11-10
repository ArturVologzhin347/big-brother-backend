package com.ithub.bigbrotherbackend.notification.core

import reactor.core.publisher.Mono

interface BaseNotificationConfigRepository<T, CLIENT_ID> {

    fun findByClientId(id: CLIENT_ID): Mono<T>

}
