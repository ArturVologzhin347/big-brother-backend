package com.ithub.bigbrotherbackend.event.scud

import com.ithub.bigbrotherbackend.event.scud.model.RawEvent

class EventManager(
    private val eventTransformer: EventTransformer,
    private val eventProducer: EventProducer,
    private val stream: EventStream
) {

    suspend fun handleEvent(rawEvent: RawEvent) = eventTransformer.transform(rawEvent).apply {
        eventProducer.produce(this)
        stream.put(this)
    }

}
