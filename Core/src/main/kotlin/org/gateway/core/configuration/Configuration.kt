package org.gateway.core.configuration

import org.gateway.adapter.configuration.AdapterConfiguration
import org.gateway.bmsController.configuration.BmsControllerConfiguration
import org.gateway.internalApi.configuration.InternalApiConfiguration
import org.gateway.publicApi.configuration.PublicApiConfiguration
import org.gateway.simulator.configuration.SimulatorConfiguration
import org.gateway.storage.configuration.StorageConfiguration
import org.gateway.storageApi.configuration.StorageApiConfiguration
import org.gateway.websocketInternalApi.configuration.WebsocketInternalApiConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableScheduling
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableConfigurationProperties
@Import(
    value = [
        AdapterConfiguration::class,
        StorageConfiguration::class,
        SimulatorConfiguration::class,
        PublicApiConfiguration::class,
        StorageApiConfiguration::class,
        InternalApiConfiguration::class,
        BmsControllerConfiguration::class,
        WebsocketInternalApiConfiguration::class
    ]
)
class Configuration
