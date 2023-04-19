package org.gateway.clients.interfaces

import org.gateway.connector.dto.SerialDataRequest

interface IClientMeasurementService {

    fun handleMeasurement(dto: SerialDataRequest)
}
