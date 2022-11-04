package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.card.CardService
import com.ithub.bigbrotherbackend.card.exception.UnknownCardNumberException
import com.ithub.bigbrotherbackend.skud.exception.WrongSkudEventTypeException
import com.ithub.bigbrotherbackend.skud.model.RawSkudEvent
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.util.logFactory
import com.ithub.bigbrotherbackend.util.toLocalDateTime
import org.springframework.stereotype.Component

@Component
class SkudEventTransformer(
    private val cardService: CardService
) {

    private val log by logFactory()

    suspend fun transform(rawSkudEvent: RawSkudEvent): SkudEvent {
        with(rawSkudEvent) {
            val linkedCard =
                cardService.queryCardByNumber(cardNumber) ?: throw UnknownCardNumberException(cardNumber)

            log.debug("Card number: $cardNumber")

            val eventType = try {
                SkudEvent.Type.valueOf(type)
            } catch (e: IllegalArgumentException) {
                throw WrongSkudEventTypeException(type)
            }

            return SkudEvent(
                type = eventType,
                timestamp = timestamp.toLocalDateTime(),
                cardId = linkedCard.id()
            )
        }

    }

}
