package org.gateway.bmsController.measurement

import org.gateway.bmsController.measurement.interfaces.IMeasurementService
import org.gateway.bmsController.connector.dto.SerialDataRequest
import org.gateway.storageApi.measurement.voltage.VoltageMeasurementService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MeasurementService @Autowired constructor(
    private val voltageMeasurementService: VoltageMeasurementService
) : IMeasurementService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handleData(dto: SerialDataRequest) {
        logger.info("Handling measurement data by serial number '${dto.serialNumber}' | ${dto.data}")
        voltageMeasurementService.saveMeasurement(
            value = dto.data.toDouble(),
            manufacturer = dto.manufacturer,
            serialNumber = dto.serialNumber
        )
    }
}
