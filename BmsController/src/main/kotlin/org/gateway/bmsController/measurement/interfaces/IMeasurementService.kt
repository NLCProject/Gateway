package org.gateway.bmsController.measurement.interfaces

import org.gateway.bmsController.connector.dto.SerialDataRequest

interface IMeasurementService {

    fun handleData(dto: SerialDataRequest)
}
