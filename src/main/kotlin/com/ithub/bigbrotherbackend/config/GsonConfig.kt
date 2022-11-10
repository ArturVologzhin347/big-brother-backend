package com.ithub.bigbrotherbackend.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GsonConfig {


//    @Bean
//    fun gson() = Gson().newBuilder().apply {
//        serializeNulls()
//    }.create()

    @Bean
    fun objectMapper() = ObjectMapper()
}