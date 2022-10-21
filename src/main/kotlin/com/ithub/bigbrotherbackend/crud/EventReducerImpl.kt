package com.ithub.bigbrotherbackend.crud

import com.ithub.bigbrotherbackend.card.CardRepository
import com.ithub.bigbrotherbackend.error.ApiException
import com.ithub.bigbrotherbackend.event.Event
import com.ithub.bigbrotherbackend.event.EventType
import com.ithub.bigbrotherbackend.event.reducer.EventReducer
import com.ithub.bigbrotherbackend.util.logFactory
import com.ithub.bigbrotherbackend.util.toLocalDateTime
import kotlinx.coroutines.flow.first
import org.springframework.stereotype.Component

@Component
class EventReducerImpl(
    private val cardRepository: CardRepository,
) : EventReducer {

    private val logger by logFactory()

    override suspend fun reduceCrudEvent(skudEvent: SkudEvent): Event {

        val cardId = cardRepository.findOneByNumber(skudEvent.cardNumber).first()?.id()
            ?: throw UnknownCardNumberException(skudEvent.cardNumber)


        logger.info("Card number: ${skudEvent.cardNumber}")

        val eventType = try {
            EventType.valueOf(skudEvent.type)
        } catch (e: Exception) {
            throw WrongEventTypeException(skudEvent.type)
        }


        return Event(
            type = eventType,
            timestamp = skudEvent.timestamp.toLocalDateTime(),
            cardId = cardId
        )

    }

}

class WrongEventTypeException(type: String) : ApiException("Type $type is wrong. Existing types: ${EventType.values()}")

class UnknownCardNumberException(cardNumber: String) : ApiException("Unknown card number: $cardNumber")
