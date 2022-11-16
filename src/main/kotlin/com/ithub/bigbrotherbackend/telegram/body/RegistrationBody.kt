package com.ithub.bigbrotherbackend.telegram.body

data class RegistrationBody(
    val chat: Int,
    val phoneNumber: String
)
