package org.gateway.bmsController.measurement.interfaces

import org.gateway.bmsController.connector.dto.SerialDataRequest
import org.gateway.storage.batterySystem.BatterySystemEntity

interface IMeasurementService {

    fun handleData(dto: SerialDataRequest, system: BatterySystemEntity)
}
