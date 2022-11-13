package com.ithub.bigbrotherbackend.card

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class CardService(
    private val cardRepository: CardRepository
) {

    suspend fun findByNumber(number: String): Card? {
        return cardRepository.findByNumber(number).awaitSingleOrNull()
    }

}

