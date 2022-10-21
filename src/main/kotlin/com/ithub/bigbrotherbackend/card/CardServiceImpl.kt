package com.ithub.bigbrotherbackend.card

import org.springframework.stereotype.Service

@Service
class CardServiceImpl(
    val cardRepository: CardRepository
) : CardService {

    override suspend fun createCard(card: Card): Card {
        return cardRepository.save(card)
    }

}
