package com.ithub.bigbrotherbackend.db

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.binding.BindMarkersFactory

@Configuration
@EnableR2dbcRepositories
class DatabaseConfig {

    @Bean
    fun databaseClient(connectionFactory: ConnectionFactory) = DatabaseClient.builder().apply {
        it.connectionFactory(connectionFactory)
        it.bindMarkers { BindMarkersFactory.named(":", "", 50).create() }
        it.namedParameters(true)
    }.build()

    @Bean
    fun initializer(databaseClient: DatabaseClient, connectionFactory: ConnectionFactory) =
        ConnectionFactoryInitializer().apply {
            setConnectionFactory(connectionFactory)
            setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
        }

}
