package com.ithub.bigbrotherbackend.event.scud

import com.ithub.bigbrotherbackend.event.EventStreamChain
import com.ithub.bigbrotherbackend.event.scud.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class EventStream(
    private vararg val chains: EventStreamChain
) {

    suspend fun put(event: Event) = coroutineScope {
        chains.forEach { chain -> withContext(Dispatchers.IO) { chain.run(event) } }
    }

}
