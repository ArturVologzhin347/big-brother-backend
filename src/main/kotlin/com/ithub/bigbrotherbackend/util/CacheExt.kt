package com.ithub.bigbrotherbackend.util

import org.springframework.cache.Cache
import org.springframework.cache.CacheManager

fun CacheManager.cached(name: String): Lazy<Cache> {
    return lazy(LazyThreadSafetyMode.NONE) {
        getCache(name) ?: throw IllegalArgumentException("Cache $name not initialized.")
    }
}
