package com.ithub.bigbrotherbackend.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfig(
    private val env: Environment
) {

//    @Bean
//    fun tcpClient() = TcpClient
//        .create()
//        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TCP_TIMEOUT.toInt())
//        .doOnConnected { connection ->
//            connection.addHandlerLast(ReadTimeoutHandler(TCP_TIMEOUT, TimeUnit.MILLISECONDS))
//            connection.addHandlerLast(WriteTimeoutHandler(TCP_TIMEOUT, TimeUnit.MILLISECONDS))
//        }

    // TODO http client configuration

    @Bean
    fun httpClient(): HttpClient = HttpClient.create()

    @Bean
    @Qualifier(QUALIFIER_WEB_CLIENT_TELEGRAM)
    fun webClientTelegram(httpClient: HttpClient) = WebClient.builder().apply {

        val baseUrl = env.getProperty(ENV_API_TELEGRAM_URL)
            ?: throw NullPointerException("No value with key $ENV_API_TELEGRAM_URL")

        it.baseUrl(baseUrl)

        it.clientConnector(ReactorClientHttpConnector(httpClient))

    }.build()


    companion object {
//        const val TCP_TIMEOUT = 1000L

        const val ENV_API_TELEGRAM_URL = "api.telegram.url"

        const val QUALIFIER_WEB_CLIENT_TELEGRAM = "webClientTelegram"

    }

}
