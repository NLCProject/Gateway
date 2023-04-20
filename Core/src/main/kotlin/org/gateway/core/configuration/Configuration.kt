package org.gateway.core.configuration

import org.gateway.bmsController.configuration.BmsConfiguration
import org.gateway.storage.configuration.StorageConfiguration
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
@Import(value = [StorageConfiguration::class, BmsConfiguration::class])
class Configuration
