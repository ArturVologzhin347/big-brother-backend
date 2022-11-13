package com.ithub.bigbrotherbackend.util

fun String.leaveOnlyDigits(): String {
    return this.replace("[^0-9]".toRegex(), "")
}

fun String.isPhoneNumber(): Boolean {
    return matches("^\\[0-9]{10,13}\$".toRegex())
}
