package com.ithub.bigbrotherbackend.sms

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "sms")
data class Sms(

    @Id
    @Column("id")
    private val _id: Long? = null,

    @Column("payload")
    val payload: String,

    @Column("status")
    val status: Status = Status.WAITING,

    @Column("high_priority")
    val highPriority: Boolean = false,

    @Column("respondent_id")
    val respondentId: Long,

    @Column("created_at")
    val createdAt: LocalDateTime? = null,

    @Column("sent_at")
    val sentAt: LocalDateTime? = null

) {

    fun id() = checkNotNull(_id)

    enum class Status {
        WAITING,
        SENT
    }

}
