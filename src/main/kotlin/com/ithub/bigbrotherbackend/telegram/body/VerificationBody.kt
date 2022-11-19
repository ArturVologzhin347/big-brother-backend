package com.ithub.bigbrotherbackend.telegram.body

data class VerificationBody(
    val chat: Int,
    val code: String
)
