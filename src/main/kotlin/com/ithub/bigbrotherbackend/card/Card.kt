package com.ithub.bigbrotherbackend.card

import com.ithub.bigbrotherbackend.base.entity.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "card")
data class Card(

    @Id
    @Column("id")
    private val _id: Long? = null,

    @Column("number")
    val number: String

) : Entity<Long>() {

    override fun id() = checkNotNull(_id)

}
