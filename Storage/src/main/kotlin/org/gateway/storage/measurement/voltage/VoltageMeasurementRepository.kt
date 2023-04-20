package org.gateway.storage.measurement.voltage

import org.gateway.storage.framework.Repository
import org.gateway.storage.measurement.voltage.interfaces.IVoltageMeasurementRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VoltageMeasurementRepository @Autowired constructor(
    repository: IVoltageMeasurementRepository
) : Repository<VoltageMeasurementEntity>(repository = repository) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun saveMeasurement(measuredValue: Double, manufacturer: String, serialNumber: String) {
        val entity = VoltageMeasurementEntity().apply {
            this.manufacturer = manufacturer
            this.serialNumber = serialNumber
            this.measuredValue = measuredValue
        }

        logger.info("Saving voltage measurement | $measuredValue")
        repository.save(entity)
    }
}
