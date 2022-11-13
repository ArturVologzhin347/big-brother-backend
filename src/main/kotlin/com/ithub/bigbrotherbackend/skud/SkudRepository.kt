package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface SkudRepository : CoroutineCrudRepository<SkudEvent, Long> {

}
