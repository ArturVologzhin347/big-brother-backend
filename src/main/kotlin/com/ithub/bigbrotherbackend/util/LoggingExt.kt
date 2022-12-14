package com.ithub.bigbrotherbackend.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T : Any> T.loggerFactory(): Lazy<Logger> {
    return lazy { LoggerFactory.getLogger(T::class.java) }
}
