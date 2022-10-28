package com.ithub.bigbrotherbackend.event.scud

import com.ithub.bigbrotherbackend.card.CardRepository
import com.ithub.bigbrotherbackend.card.exception.UnknownCardNumberException
import com.ithub.bigbrotherbackend.event.exception.WrongEventTypeException
import com.ithub.bigbrotherbackend.event.scud.model.Event
import com.ithub.bigbrotherbackend.event.scud.model.RawEvent
import com.ithub.bigbrotherbackend.util.logFactory
import com.ithub.bigbrotherbackend.util.toLocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class EventTransformer(
    private val cardRepository: CardRepository
) {

    private val log by logFactory()

    suspend fun transform(rawEvent: RawEvent): Event {

        val linkedCard = withContext(Dispatchers.IO) {
            cardRepository.findOneByNumber(rawEvent.cardNumber).block()
        } ?: throw UnknownCardNumberException(rawEvent.cardNumber)

        log.debug("Card number: ${rawEvent.cardNumber}")

        val eventType = try {
            Event.Type.valueOf(rawEvent.type)
        } catch (e: IllegalArgumentException) {
            throw WrongEventTypeException(rawEvent.type)
        }

        return Event(
            type = eventType,
            timestamp = rawEvent.timestamp.toLocalDateTime(),
            cardId = linkedCard.id()
        )

    }

}
