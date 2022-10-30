package com.ithub.bigbrotherbackend.event.handle

import com.ithub.bigbrotherbackend.event.RawEvent

open class EventAdapter<R : RawEvent, E : Any>(
    private val transformer: EventTransformer<R, E>,
    private val stream: EventStream<E>
) {

    suspend fun makeEvent(rawEvent: R) =
        putToStream(transformer.transform(rawEvent))

    protected open suspend fun putToStream(event: E) =
        stream.handleEvent(event)

}
