package com.ithub.bigbrotherbackend.crud

import com.ithub.bigbrotherbackend.event.EventService
import com.ithub.bigbrotherbackend.event.reducer.EventReducer
import com.ithub.bigbrotherbackend.util.logFactory
import org.springframework.stereotype.Service

@Service
class SkudServiceImpl(
    private val eventService: EventService,
    private val eventReducer: EventReducer
) : SkudService {

    private val logger by logFactory()

    override suspend fun handleCrudRequest(skudEvent: SkudEvent) {
        val event = eventReducer.reduceCrudEvent(skudEvent)

        logger.info(event.toString())


        /*
        TODO
        - Handle notifications
        ...
         */

        eventService.saveEvent(event)
    }

}
