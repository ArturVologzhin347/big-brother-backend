package com.ithub.bigbrotherbackend.telegram.model

data class TelegramToken(
    val configId: Long,
    val chat: Int,
    val code: String,
    val attempts: Int,
    val timestamp: Long
)