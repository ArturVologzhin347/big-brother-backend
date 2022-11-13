package com.ithub.bigbrotherbackend.telegram.body

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistrationTelegramUserBody(
    @JsonProperty("phoneNumber")
    val phoneNumber: String,

    @JsonProperty("chat")
    val chat: Long

)
