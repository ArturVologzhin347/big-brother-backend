package com.ithub.bigbrotherbackend.util

import java.util.Optional

fun <T> Optional<T>.toKtNullable(): T? {
    return if (isEmpty) null else get()
}
