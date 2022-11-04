package com.ithub.bigbrotherbackend.skud.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RawSkudEvent(

    @JsonProperty("card_number")
    val cardNumber: String,

    @JsonProperty("type")
    val type: String,

    @JsonProperty("timestamp")
    val timestamp: Long

)
