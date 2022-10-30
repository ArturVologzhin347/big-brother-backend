package com.ithub.bigbrotherbackend.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Long.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())

fun LocalDateTime.toLong(): Long =
    atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
