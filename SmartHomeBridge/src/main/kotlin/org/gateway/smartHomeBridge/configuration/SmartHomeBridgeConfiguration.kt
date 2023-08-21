package org.gateway.smartHomeBridge.configuration

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableScheduling
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableConfigurationProperties
@EntityScan(basePackages = ["org.gateway.smartHomeBridge"])
@ComponentScan(basePackages = ["org.gateway.smartHomeBridge"])
class SmartHomeBridgeConfiguration
