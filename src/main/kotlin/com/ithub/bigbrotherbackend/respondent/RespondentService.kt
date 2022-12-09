package com.ithub.bigbrotherbackend.respondent

import com.ithub.bigbrotherbackend.respondent.model.Respondent
import com.ithub.bigbrotherbackend.respondent.model.RespondentConfig
import com.ithub.bigbrotherbackend.util.cached
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RespondentService(
    private val respondentRepository: RespondentRepository,
    private val respondentConfigRepository: RespondentConfigRepository,
    cache: CacheManager
) {

    private val cacheRespondents by cache.cached("respondents")
    private val cacheRespondentConfigs by cache.cached("respondent_configs")

    // TODO evict
    @Cacheable("respondents", key = "#studentId")
    fun findRespondentsBy(studentId: String): Flow<Respondent> {
        return respondentRepository.findRespondentsBy(studentId)
    }

    // TODO evict
    @Cacheable("respondent_configs", key = "#respondentId")
    fun findRespondentConfigBy(respondentId: Long): Mono<RespondentConfig> {
        return respondentConfigRepository.findOneBy(respondentId)
    }

    suspend fun findRespondentBy(phoneNumber: String): Respondent? {
        return respondentRepository.findRespondentsByPhoneNumber(phoneNumber).awaitSingleOrNull()
    }

}
