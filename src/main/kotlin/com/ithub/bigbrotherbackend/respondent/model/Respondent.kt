package com.ithub.bigbrotherbackend.respondent.model

import com.ithub.bigbrotherbackend.base.entity.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "respondent")
data class Respondent(

    @Id
    @Column("id")
    private val _id: Long? = null,

    @Column("name")
    val name: String,

    @Column("surname")
    val surname: String,

    @Column("patronymic")
    val patronymic: String,

    @Column("phone_number")
    val phoneNumber: String

): Entity<Long>() {

    override fun id() = checkNotNull(_id)

}
