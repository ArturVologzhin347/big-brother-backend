package com.ithub.bigbrotherbackend.student.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "student")
data class Student(

    @Id
    @Column("id")
    val id: String,

    @Column("name")
    val name: String,

    @Column("surname")
    val surname: String,

    @Column("patronymic")
    val patronymic: String,

    @Column("card_id")
    val cardId: Long? = null

)