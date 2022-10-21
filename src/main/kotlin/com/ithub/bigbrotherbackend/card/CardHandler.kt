package com.ithub.bigbrotherbackend.card

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class CardHandler(
    private val cardService: CardService
) {

    suspend fun createNewCard(request: ServerRequest): ServerResponse {
        val body = request.awaitBody<BodyNewCard>()

        val card =cardService.createCard(Card(number = body.number))

        return ServerResponse
            .ok()
            .bodyValueAndAwait(card)
    }

    private class BodyNewCard(val number: String)
}
