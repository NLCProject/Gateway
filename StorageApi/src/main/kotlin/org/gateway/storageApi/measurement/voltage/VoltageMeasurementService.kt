package org.gateway.storageApi.measurement.voltage

import org.gateway.storage.batterySystem.BatterySystemEntity
import org.gateway.storage.measurement.voltage.VoltageMeasurementRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VoltageMeasurementService @Autowired constructor(
    private val repository: VoltageMeasurementRepository
) {

    fun saveMeasurement(value: Double, manufacturer: String, serialNumber: String, system: BatterySystemEntity) {
        repository.saveMeasurement(
            value = value,
            system = system,
            serialNumber = serialNumber,
            manufacturer = manufacturer
        )
    }
}
