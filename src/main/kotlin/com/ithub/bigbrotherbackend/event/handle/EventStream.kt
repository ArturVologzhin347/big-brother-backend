package com.ithub.bigbrotherbackend.event.handle

open class EventStream<E : Any>(
    private vararg val chains: EventStreamChain<E>
) {

    suspend fun handleEvent(event: E) =
        chains.forEach { chain -> chain.run(event) }

}
