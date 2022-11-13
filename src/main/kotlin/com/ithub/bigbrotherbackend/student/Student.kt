package com.ithub.bigbrotherbackend.student

import com.ithub.bigbrotherbackend.base.entity.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "student")
data class Student(

    @Id
    @Column("id")
    private val _id: String? = null,

    @Column("name")
    val name: String,

    @Column("surname")
    val surname: String,

    @Column("patronymic")
    val patronymic: String

): Entity<String>() {

    override fun id() = checkNotNull(_id)

}
