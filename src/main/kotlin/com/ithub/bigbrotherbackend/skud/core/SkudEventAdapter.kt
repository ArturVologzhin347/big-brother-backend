package com.ithub.bigbrotherbackend.skud.core

import com.ithub.bigbrotherbackend.event.RawEvent
import com.ithub.bigbrotherbackend.event.handle.EventAdapter
import com.ithub.bigbrotherbackend.event.handle.EventStream
import com.ithub.bigbrotherbackend.event.handle.EventTransformer
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.skud.SkudEventRepository

class SkudEventAdapter(
    transformer: EventTransformer<RawEvent.RawSkudEvent, SkudEvent>,
    stream: EventStream<SkudEvent>,
    private val skudEventRepository: SkudEventRepository
) : EventAdapter<RawEvent.RawSkudEvent, SkudEvent>(transformer, stream) {

    override suspend fun putToStream(event: SkudEvent) {
        super.putToStream(skudEventRepository.save(event))
    }

}
