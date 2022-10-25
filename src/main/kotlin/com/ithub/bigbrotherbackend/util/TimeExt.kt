package com.ithub.bigbrotherbackend.util

import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone

fun Long.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), TimeZone.getDefault().toZoneId())
