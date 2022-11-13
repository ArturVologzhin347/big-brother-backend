package com.ithub.bigbrotherbackend.web

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfig(env: Environment) {
    private val telegramApiUrl = env.getRequiredProperty(ENV_API_TELEGRAM_URL)

    @Bean
    fun httpClient(): HttpClient = HttpClient.create() // TODO http client configuration

    @Bean
    @Qualifier("web-client-telegram")
    fun webClientTelegram(httpClient: HttpClient) = WebClient.builder().apply {
        it.baseUrl(telegramApiUrl)
        it.clientConnector(ReactorClientHttpConnector(httpClient))

    }.build()


    companion object {
//        const val TCP_TIMEOUT = 1000L

        const val ENV_API_TELEGRAM_URL = "api.telegram.url"
    }

}
