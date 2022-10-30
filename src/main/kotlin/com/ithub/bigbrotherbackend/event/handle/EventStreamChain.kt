package com.ithub.bigbrotherbackend.event.handle

interface EventStreamChain<E: Any> {
    suspend fun run(event: E)
}
