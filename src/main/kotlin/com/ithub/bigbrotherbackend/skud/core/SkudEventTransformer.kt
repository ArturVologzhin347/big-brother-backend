package com.ithub.bigbrotherbackend.skud.core

import com.ithub.bigbrotherbackend.card.CardService
import com.ithub.bigbrotherbackend.card.exception.UnknownCardNumberException
import com.ithub.bigbrotherbackend.event.RawEvent
import com.ithub.bigbrotherbackend.event.handle.EventTransformer
import com.ithub.bigbrotherbackend.skud.exception.WrongSkudEventTypeException
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.util.logFactory
import com.ithub.bigbrotherbackend.util.toLocalDateTime

class SkudEventTransformer(
    private val cardService: CardService
) : EventTransformer<RawEvent.RawSkudEvent, SkudEvent> {

    private val log by logFactory()

    override suspend fun transform(rawEvent: RawEvent.RawSkudEvent): SkudEvent {
        val linkedCard =
            cardService.queryCardByNumber(rawEvent.cardNumber) ?: throw UnknownCardNumberException(rawEvent.cardNumber)

        log.debug("Card number: ${rawEvent.cardNumber}")

        val eventType = try {
            SkudEvent.Type.valueOf(rawEvent.type)
        } catch (e: IllegalArgumentException) {
            throw WrongSkudEventTypeException(rawEvent.type)
        }

        return SkudEvent(
            type = eventType,
            timestamp = rawEvent.timestamp.toLocalDateTime(),
            cardId = linkedCard.id()
        )

    }

}
