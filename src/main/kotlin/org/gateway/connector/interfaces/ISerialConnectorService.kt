package org.gateway.connector.interfaces

import org.gateway.connector.dto.SerialDataResponse
import org.gateway.connector.events.SendSerialResponseEvent
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled

interface ISerialConnectorService {

    @Scheduled(cron = "*/30 * * * * *")
    @EventListener(ApplicationReadyEvent::class)
    fun initialize()

    @EventListener(SendSerialResponseEvent::class)
    fun sendData(dto: SerialDataResponse)

    fun closeConnection()
}
