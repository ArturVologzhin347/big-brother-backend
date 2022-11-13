package com.ithub.bigbrotherbackend.respondent.model

import com.ithub.bigbrotherbackend.base.entity.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "respondent_config")
data class RespondentConfig(

    @Id
    @Column("id")
    private val _id: Long? = null,

    @Column("respondent_id")
    private val respondentId: Long


) : Entity<Long>() {

    override fun id() = checkNotNull(_id)

}
