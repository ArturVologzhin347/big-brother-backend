package com.ithub.bigbrotherbackend.card

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class CardServiceImpl(
    private val cardRepository: CardRepository
): CardService {

    override suspend fun findAllCards(): Flow<Card> {
        return cardRepository.findAll()
    }


}