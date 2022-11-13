package com.ithub.bigbrotherbackend.telegram.body

import com.fasterxml.jackson.annotation.JsonProperty

data class VerifyTelegramClientBody(
    @JsonProperty("chat")
    val chat: Long,

    @JsonProperty("code")
    val code: String

)
