package com.ithub.bigbrotherbackend.card

import kotlinx.coroutines.flow.Flow


interface CardService {
    suspend fun findAllCards(): Flow<Card>
}