package com.ithub.bigbrotherbackend.util

fun String.leaveOnlyDigits(): String {
    return this.replace("[^0-9]".toRegex(), "")
}
