package org.gateway.bmsController.connector.interfaces

import org.gateway.bmsController.connector.dto.SerialDataResponse
import org.gateway.bmsController.connector.events.SendSerialResponseEvent
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener

interface ISerialConnectorService {

    @EventListener(ApplicationReadyEvent::class)
    fun initialize()

    @EventListener(SendSerialResponseEvent::class)
    fun sendData(dto: SerialDataResponse)

    fun closeConnection()
}
