package com.ithub.bigbrotherbackend.telegram.origin

sealed class TelegramResult {

    class Failure(
        val code: FailureCode,
        val message: String
    ) : TelegramResult()

    object Success : TelegramResult()

    enum class FailureCode {
        BOT_IS_DOWN,
        INNER_FAILURE,
        CHAT_NOT_FOUND,
        UNKNOWN
    }

}
