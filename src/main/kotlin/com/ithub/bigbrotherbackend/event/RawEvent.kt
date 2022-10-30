package com.ithub.bigbrotherbackend.event

import com.fasterxml.jackson.annotation.JsonProperty

sealed class RawEvent {

    data class RawSkudEvent(

        @JsonProperty("card_number")
        val cardNumber: String,

        @JsonProperty("type")
        val type: String,

        @JsonProperty("timestamp")
        val timestamp: Long

    ): RawEvent()

}
