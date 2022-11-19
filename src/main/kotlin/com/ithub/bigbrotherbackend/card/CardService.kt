package com.ithub.bigbrotherbackend.card

import com.ithub.bigbrotherbackend.util.cached
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CardService(
    private val cardRepository: CardRepository,
    cache: CacheManager
) {

    private val cacheCards by cache.cached("cards")

    @Cacheable("cards", key = "#number") // TODO evict
    suspend fun findByNumber(number: String): Card? {
        return cardRepository.findByNumber(number).awaitSingleOrNull()
    }

}
