package com.ithub.bigbrotherbackend.card

import kotlinx.coroutines.flow.Flow


interface CardService {

    suspend fun queryAllCards(): Flow<Card>

    suspend fun queryOneById(id: Long): Card

    suspend fun queryCardByNumber(number: String): Card?

}
