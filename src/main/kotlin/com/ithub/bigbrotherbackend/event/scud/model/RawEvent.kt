package com.ithub.bigbrotherbackend.event.scud.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RawEvent(
    val type: String,
    val timestamp: Long,

    @JsonProperty("card_number")
    val cardNumber: String
)
