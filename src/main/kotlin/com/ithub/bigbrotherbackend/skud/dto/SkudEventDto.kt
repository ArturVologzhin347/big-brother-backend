package com.ithub.bigbrotherbackend.skud.dto

import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.util.toLong

class SkudEventDto(
    val id: Long,
    val type: SkudEvent.Type,
    val timestamp: Long
 ) {

    companion object {
        fun from(event: SkudEvent) = SkudEventDto(
            id = event.id(),
            type = event.type,
            timestamp = event.timestamp.toLong()
        )
    }

}
