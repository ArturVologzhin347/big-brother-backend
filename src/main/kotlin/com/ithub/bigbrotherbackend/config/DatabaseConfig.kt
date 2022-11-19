package com.ithub.bigbrotherbackend.config

import com.ithub.bigbrotherbackend.Profiles
import com.ithub.bigbrotherbackend.util.setPopulatorFromResources
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.binding.BindMarkersFactory

@Configuration
@EnableR2dbcRepositories
class DatabaseConfig {

    @Bean
    fun databaseClient(connectionFactory: ConnectionFactory) = DatabaseClient.builder().apply {
        it.connectionFactory(connectionFactory)
        it.bindMarkers {
            BindMarkersFactory.named(
                MARKER_PREFIX,
                MARKER_NAME_PREFIX,
                MARKER_MAX_LENGTH
            ).create()
        }
        it.namedParameters(true)
    }.build()

    @Bean
    @Profile(Profiles.DEV)
    fun initializeDev(databaseClient: DatabaseClient, connectionFactory: ConnectionFactory) =
        ConnectionFactoryInitializer().apply {
            setConnectionFactory(connectionFactory)
            setPopulatorFromResources(
                PATH_DROP, // TODO delete path to drop public databases
                PATH_SCHEMA,
                PATH_SCHEMA_TELEGRAM,
                PATH_DATA
            )
        }

    @Bean
    @Profile(Profiles.PROD)
    fun initializeProd(databaseClient: DatabaseClient, connectionFactory: ConnectionFactory) =
        ConnectionFactoryInitializer().apply {
            setConnectionFactory(connectionFactory)
            setPopulatorFromResources(
                PATH_SCHEMA,
                PATH_SCHEMA_TELEGRAM
            )
        }

    companion object {
        private const val PATH_SQL = "sql"

        private const val PATH_SCHEMA = "$PATH_SQL/schema.sql"
        private const val PATH_SCHEMA_TELEGRAM = "$PATH_SQL/schema_telegram.sql"
        private const val PATH_DATA = "$PATH_SQL/data.sql"
        private const val PATH_DROP = "$PATH_SQL/drop.sql"

        private const val MARKER_PREFIX = ":"
        private const val MARKER_NAME_PREFIX = ""
        private const val MARKER_MAX_LENGTH = 32

    }

}
