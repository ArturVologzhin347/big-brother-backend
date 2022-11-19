package com.ithub.bigbrotherbackend.telegram.core

sealed class TelegramResult {

    object Success : TelegramResult()

    class Failure(
        val code: ErrorCode,
        val message: String
    ) : TelegramResult()

    enum class ErrorCode {
        BOT_IS_DOWN,
        BOT_INTERNAL_ERROR,
        CHAT_UNAVAILABLE
    }

}
