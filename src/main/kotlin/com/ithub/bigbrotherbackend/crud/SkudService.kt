package com.ithub.bigbrotherbackend.crud

interface SkudService {
    suspend fun handleCrudRequest(skudEvent: SkudEvent)
}