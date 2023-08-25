package org.gateway.storage.measurement.voltage

import org.gateway.storage.batterySystem.BatterySystemEntity
import org.gateway.storage.framework.Repository
import org.gateway.storage.measurement.voltage.interfaces.IVoltageMeasurementRepository
import org.gateway.websocketInternalApi.InternalWebsocketSessionSender
import org.gateway.websocketInternalApi.messages.VoltageMeasurement
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class VoltageMeasurementRepository @Autowired constructor(
    private val repository: IVoltageMeasurementRepository,
    private val sessionSender: InternalWebsocketSessionSender
) : Repository<VoltageMeasurementEntity>(repository = repository) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun saveMeasurement(value: Double, manufacturer: String, serialNumber: String, system: BatterySystemEntity) {
        val entity = VoltageMeasurementEntity().apply {
            this.system = system
            this.measuredValue = value
            this.manufacturer = manufacturer
            this.serialNumber = serialNumber
        }

        logger.info("Saving voltage measurement | $value")
        this.save(entity)

        VoltageMeasurement(serialNumber = serialNumber, manufacturer = manufacturer, value = value)
            .apply { sessionSender.sendMessage(message = this) }
    }
}
