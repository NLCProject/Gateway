package org.gateway.bmsController.connector

import org.gateway.bmsController.JsonSerialization
import org.gateway.bmsController.measurement.interfaces.IMeasurementService
import org.gateway.bmsController.connector.dto.SerialDataRequest
import org.gateway.bmsController.connector.dto.SerialDataCommand
import org.gateway.bmsController.connector.interfaces.ISerialDataHandler
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SerialDataHandler @Autowired constructor(
    private val measurementService: IMeasurementService
): ISerialDataHandler, JsonSerialization() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handleData(data: String) {
        try {
            logger.trace("Received input data | $data")
            val dto = decode<SerialDataRequest>(data = data)

            when (dto.command) {
                SerialDataCommand.Measurement -> measurementService.handleData(dto = dto)
            }
        } catch (exception: Exception) {
            logger.trace("Error while handling serial data | ${exception.message}")
        }
    }
}