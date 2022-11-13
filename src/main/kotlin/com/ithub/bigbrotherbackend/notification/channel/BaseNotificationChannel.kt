package com.ithub.bigbrotherbackend.notification.channel

import com.ithub.bigbrotherbackend.db.Entity
import com.ithub.bigbrotherbackend.notification.core.BaseNotificationClientRepository
import com.ithub.bigbrotherbackend.notification.core.BaseNotificationConfigRepository
import kotlinx.coroutines.reactor.awaitSingle


abstract class BaseNotificationChannel<CLIENT_ID, CLIENT: Entity<CLIENT_ID>, CONFIG>(
    private val clientRepository: BaseNotificationClientRepository<CLIENT>,
    private val configRepository: BaseNotificationConfigRepository<CONFIG, CLIENT_ID>
) {

    protected suspend fun getClientAndConfig(respondentId: Long): Pair<CLIENT, CONFIG> {
        val client = clientRepository.findByRespondentId(respondentId).awaitSingle()
        val config = configRepository.findByClientId(client.id()).awaitSingle()
        return client to config
    }

}
