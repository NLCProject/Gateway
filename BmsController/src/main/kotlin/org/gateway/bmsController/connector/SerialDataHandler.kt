package org.gateway.bmsController.connector

import org.gateway.bmsController.connector.dto.SerialDataCommand
import org.gateway.bmsController.connector.dto.SerialDataRequest
import org.gateway.bmsController.connector.interfaces.ISerialDataHandler
import org.gateway.bmsController.measurement.interfaces.IMeasurementService
import org.gateway.storageApi.batterySystem.BatterySystemService
import org.gateway.utils.serialzation.JsonSerialization
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SerialDataHandler @Autowired constructor(
    private val measurementService: IMeasurementService,
    private val batterySystemService: BatterySystemService
) : ISerialDataHandler, JsonSerialization() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handleData(data: String) {
        logger.trace("Received input data | $data")
        data.split("\r\n")
            .filter { it.isNotBlank() && it.isNotEmpty() }
            .forEach {
                try {
                    val dto = decode<SerialDataRequest>(data = it)
                    handleData(dto = dto)
                } catch (exception: Exception) {
                    logger.trace("Error while decoding serial data | ${exception.message}")
                }
            }
    }

    override fun handleData(dto: SerialDataRequest) {
        try {
            logger.trace("Handling serial data of serial number '${dto.serialNumber}'")
            batterySystemService.addSystemIfNotExisting(
                manufacturer = dto.manufacturer,
                serialNumber = dto.serialNumber
            )

            when (dto.command) {
                SerialDataCommand.Measurement -> measurementService.handleData(dto = dto)
            }
        } catch (exception: Exception) {
            logger.trace("Error while handling serial data | ${exception.message}")
        }
    }
}
