package org.gateway.connector

import org.gateway.clients.interfaces.IClientMeasurementService
import org.gateway.connector.dto.SerialDataRequest
import org.gateway.connector.dto.SerialDataCommand
import org.gateway.connector.interfaces.ISerialDataHandler
import org.gateway.services.serialization.JsonSerialization
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SerialDataHandler @Autowired constructor(
    private val clientMeasurementService: IClientMeasurementService
): ISerialDataHandler, JsonSerialization() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handleData(data: String) {
        try {
            logger.trace("Received input data | $data")
            val dto = decode<SerialDataRequest>(data = data)

            when (dto.command) {
                SerialDataCommand.Measurement -> clientMeasurementService.handleMeasurement(dto = dto)
            }
        } catch (exception: Exception) {
            logger.trace("Error while handling serial data | ${exception.message}")
        }
    }
}
