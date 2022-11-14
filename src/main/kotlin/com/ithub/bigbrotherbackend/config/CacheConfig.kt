package com.ithub.bigbrotherbackend.config


import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@Configuration
class CacheConfig {

    @Bean
    fun caffeine(): Caffeine<Any, Any> = Caffeine.newBuilder().apply {
        expireAfterWrite(60, TimeUnit.MINUTES)
    }

    @Bean
    fun cacheManager(caffeine: Caffeine<Any, Any>): CacheManager = CaffeineCacheManager().apply {
        isAllowNullValues = false
        setCaffeine(caffeine)
    }

}
