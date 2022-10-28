package com.ithub.bigbrotherbackend.util

import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator


fun ConnectionFactoryInitializer.setPopulatorFromResources(vararg path: String) = setDatabasePopulator(
        ResourceDatabasePopulator(*path.map { ClassPathResource(it) }.toTypedArray())
    )
