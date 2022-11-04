package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.PageRequest
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface SkudEventRepository : CoroutineSortingRepository<SkudEvent, Long> {

    fun findBy(pageRequest: PageRequest): Flow<SkudEvent>

}
