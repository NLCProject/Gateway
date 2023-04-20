package org.gateway.storageApi.batterySystem

import org.gateway.storage.batterySystem.BatterySystemRepository
import org.gateway.storageApi.batterySystem.converter.BatterySystemConverter
import org.gateway.storageApi.batterySystem.dto.BatterySystemDto
import org.gateway.utils.battery.enums.SystemStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BatterySystemService @Autowired constructor(
    private val repository: BatterySystemRepository
) {

    fun addSystemIfNotExisting(manufacturer: String, serialNumber: String) {
        repository.addSystemIfNotExisting(manufacturer = manufacturer, serialNumber = serialNumber)
    }

    fun changeStatus(manufacturer: String, serialNumber: String, newStatus: SystemStatus) {
        repository.changeStatus(manufacturer = manufacturer, serialNumber = serialNumber, newStatus = newStatus)
    }

    fun findAllByStatus(status: List<SystemStatus>): List<BatterySystemDto> = repository
        .findAllByStatus(status = status)
        .map { BatterySystemConverter.convert(entity = it) }
}
