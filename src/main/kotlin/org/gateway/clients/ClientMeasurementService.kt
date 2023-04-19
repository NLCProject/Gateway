package org.gateway.clients

import org.gateway.clients.interfaces.IClientMeasurementService
import org.gateway.connector.dto.SerialDataRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ClientMeasurementService : IClientMeasurementService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handleMeasurement(dto: SerialDataRequest) {
        logger.info("Handling measurement data by serial number '${dto.serialNumber}' | Value -> ${dto.data}")
    }
}
