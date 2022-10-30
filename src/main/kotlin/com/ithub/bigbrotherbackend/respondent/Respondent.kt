package com.ithub.bigbrotherbackend.respondent

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
    val phoneNumber: String,

    @Column("student_id")
    val studentId: Long? = null

) {

    fun id() = checkNotNull(_id)

}
