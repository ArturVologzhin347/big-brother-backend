package com.ithub.bigbrotherbackend.event.handle

import com.ithub.bigbrotherbackend.event.RawEvent

interface EventTransformer<R: RawEvent, E: Any> {
    suspend fun transform(rawEvent: R): E
}
