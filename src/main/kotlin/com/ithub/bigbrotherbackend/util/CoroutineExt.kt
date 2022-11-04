package com.ithub.bigbrotherbackend.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import reactor.core.publisher.Mono


suspend fun <T> Flow<T>.await(): List<T> = withContext(Dispatchers.IO) { this@await.toList() }

