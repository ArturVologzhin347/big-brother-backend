package com.ithub.bigbrotherbackend.card

interface CardService {
    suspend fun createCard(card: Card): Card
}