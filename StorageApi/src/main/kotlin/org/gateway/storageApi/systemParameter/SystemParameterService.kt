package org.gateway.storageApi.systemParameter

import org.gateway.storage.batterySystem.BatterySystemRepository
import org.gateway.storage.measurement.voltage.VoltageMeasurementRepository
import org.gateway.storageApi.batterySystem.dto.BatteryParameterDto
import org.gateway.storageApi.batterySystem.dto.BatteryParameterSummaryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class SystemParameterService @Autowired constructor(
    private val batterySystemRepository: BatterySystemRepository,
    private val voltageMeasurementRepository: VoltageMeasurementRepository
) {

    @OptIn(ExperimentalStdlibApi::class)
    fun getSummary(): BatteryParameterSummaryDto {
        val systems = batterySystemRepository
            .findAll()
            .map {
                val measuredValue = voltageMeasurementRepository
                    .getLatestBySerialNumber(it.serialNumber)
                    .getOrNull()
                    ?.measuredValue
                    ?: 0.0

                BatteryParameterDto().apply {
                    this.status = it.status
                    this.measuredValue = measuredValue
                    this.serialNumber = it.serialNumber
                    this.manufacturer = it.manufacturer
                }
            }
            // Assume that battery cells with no voltage are not operating and disabled by hardware
            .filter { it.measuredValue > 0.0 }

        return BatteryParameterSummaryDto().apply {
            this.systems = systems
            // Assume that all battery cells are connected in parallel. This means the least-powerful battery cell
            // defines the operating voltage of the battery
            this.voltage = systems.minOf { it.measuredValue }
        }
    }
}
