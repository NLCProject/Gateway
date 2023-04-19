package org.gateway.bmsController.measurement

import org.gateway.bmsController.measurement.interfaces.IMeasurementService
import org.gateway.bmsController.connector.dto.SerialDataRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MeasurementService : IMeasurementService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handleData(dto: SerialDataRequest) {
        logger.info("Handling measurement data by serial number '${dto.serialNumber}' | Value -> ${dto.data}")
    }
}
