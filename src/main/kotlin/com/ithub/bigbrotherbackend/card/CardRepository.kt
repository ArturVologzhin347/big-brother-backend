package com.ithub.bigbrotherbackend.card

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CardRepository : CoroutineCrudRepository<Card, Long> {


    @Query("SELECT * FROM card WHERE number=:number")
     fun findOneByNumber(number: String): Flow<Card?>

}
