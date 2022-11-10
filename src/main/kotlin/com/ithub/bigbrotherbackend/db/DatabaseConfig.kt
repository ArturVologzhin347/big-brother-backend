package com.ithub.bigbrotherbackend.db

import com.ithub.bigbrotherbackend.Profiles
import com.ithub.bigbrotherbackend.util.setPopulatorFromResources
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
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
                PATH_TYPES,
                PATH_SCHEMA,
                PATH_SCHEMA_SKUD,
                PATH_SCHEMA_TELEGRAM,
                PATH_SCHEMA_SMS,
                PATH_DATA
            )
        }

    @Bean
    @Profile(Profiles.PROD)
    fun initializeProd(databaseClient: DatabaseClient, connectionFactory: ConnectionFactory) =
        ConnectionFactoryInitializer().apply {
            setConnectionFactory(connectionFactory)
            setPopulatorFromResources(
                PATH_TYPES,
                PATH_SCHEMA,
                PATH_SCHEMA_SKUD,
                PATH_SCHEMA_TELEGRAM,
                PATH_SCHEMA_SMS
            )
        }


    companion object {
        private const val PATH_SQL = "sql"

        private const val PATH_SCHEMA = "$PATH_SQL/schema.sql"
        private const val PATH_SCHEMA_SKUD = "$PATH_SQL/schema_skud.sql"
        private const val PATH_SCHEMA_TELEGRAM = "$PATH_SQL/schema_telegram.sql"
        private const val PATH_SCHEMA_SMS = "$PATH_SQL/schema_sms.sql"
        private const val PATH_TYPES = "$PATH_SQL/types.sql"
        private const val PATH_DATA = "$PATH_SQL/data.sql"
        private const val PATH_DROP = "$PATH_SQL/drop.sql"

        private const val MARKER_PREFIX = ":"
        private const val MARKER_NAME_PREFIX = ""
        private const val MARKER_MAX_LENGTH = 32

    }

}
