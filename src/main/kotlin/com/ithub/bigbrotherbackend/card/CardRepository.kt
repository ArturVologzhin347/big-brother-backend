package com.ithub.bigbrotherbackend.card

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CardRepository: CoroutineCrudRepository<Card, Long> {

}
