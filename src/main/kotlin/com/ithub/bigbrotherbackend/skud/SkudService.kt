package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import org.springframework.stereotype.Service

@Service
class SkudService {

    suspend fun acceptSkudEvent(
        cardNumber: String,
        type: SkudEvent.Type,
        timestamp: Long
    ) {
        TODO()
    }

}
