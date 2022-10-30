package com.ithub.bigbrotherbackend.card

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class CardServiceImpl(
    private val cardRepository: CardRepository
) : CardService {

    override suspend fun queryAllCards(): Flow<Card> {
        return cardRepository.findAll()
    }

    override suspend fun queryOneById(id: Long): Card {
        return cardRepository.findById(id) ?: throw NullPointerException("Card with id:$id not exists.")
    }

    override suspend fun queryCardByNumber(number: String): Card? {
        return cardRepository.findOneByNumber(number).awaitSingleOrNull()
    }

}
