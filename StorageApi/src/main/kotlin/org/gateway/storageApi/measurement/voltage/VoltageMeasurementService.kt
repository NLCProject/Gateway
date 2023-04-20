package org.gateway.storageApi.measurement.voltage

import org.gateway.storage.measurement.voltage.VoltageMeasurementRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VoltageMeasurementService @Autowired constructor(
    private val repository: VoltageMeasurementRepository
) {

    fun saveMeasurement(measuredValue: Double, manufacturer: String, serialNumber: String) {
        repository.saveMeasurement(
            serialNumber = serialNumber,
            manufacturer = manufacturer,
            measuredValue = measuredValue
        )
    }
}
