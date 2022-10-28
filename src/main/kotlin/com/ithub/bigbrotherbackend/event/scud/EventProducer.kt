package com.ithub.bigbrotherbackend.event.scud

import com.ithub.bigbrotherbackend.event.EventRepository
import com.ithub.bigbrotherbackend.event.scud.model.Event

class EventProducer(
    private val eventRepository: EventRepository
) {

    suspend fun produce(event: Event) {
        eventRepository.save(event)
    }




}