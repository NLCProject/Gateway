package org.gateway.storageApi.consumerGroup.converter

import org.gateway.storage.batterySystem.BatterySystemEntity
import org.gateway.storage.consumerGroup.ConsumerGroupEntity
import org.gateway.storage.consumerGroup.WiringMode
import org.gateway.storageApi.batterySystem.converter.BatterySystemConverter
import org.gateway.storageApi.consumerGroup.dto.ConsumerGroupDto
import org.gateway.storageApi.dto.DtoConverter

object ConsumerGroupConverter {

    fun convert(entity: ConsumerGroupEntity): ConsumerGroupDto = ConsumerGroupDto().apply {
        name = entity.name
        mode = entity.mode
        wiring = entity.wiring
        standard = entity.standard
        voltage = calculateVoltage(entity)
        systems = entity.systems.map { BatterySystemConverter.convert(it) }
        DtoConverter.convert(entity = entity, dto = this)
    }

    private fun calculateVoltage(entity: ConsumerGroupEntity): Double = when (entity.wiring) {
        WiringMode.Unknown -> 0.0
        WiringMode.Serial -> entity.systems.sumOf { getLatestMeasurement(it) }
        WiringMode.Parallel -> entity
            .systems
            .map { getLatestMeasurement(it) }
            .filter { it > 0 }
            .minOrNull()
            ?: 0.0
    }

    private fun getLatestMeasurement(system: BatterySystemEntity): Double {
        system.measurements.sortByDescending { it.timestampCreated }
        return system.measurements.firstOrNull()?.measuredValue ?: 0.0
    }
}
