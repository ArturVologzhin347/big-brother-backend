package com.ithub.bigbrotherbackend.db

interface Entity<ID> {
    fun id(): ID
}
