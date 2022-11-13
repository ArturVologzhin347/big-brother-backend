package com.ithub.bigbrotherbackend.skud.body

import com.fasterxml.jackson.annotation.JsonProperty

data class AcceptSkudEventBody(

    @JsonProperty("number")
    val cardNumber: String,

    @JsonProperty("type")
    val type: String,

    @JsonProperty("timestamp")
    val timestamp: Long

)
