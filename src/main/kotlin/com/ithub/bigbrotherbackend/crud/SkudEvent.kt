package com.ithub.bigbrotherbackend.crud

import com.fasterxml.jackson.annotation.JsonProperty


data class SkudEvent(
    @JsonProperty("card_number") val cardNumber: String,
    @JsonProperty("timestamp") val timestamp: Long,
    @JsonProperty("type") val type: String
)
