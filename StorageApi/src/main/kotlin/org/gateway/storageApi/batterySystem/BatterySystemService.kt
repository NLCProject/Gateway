package org.gateway.storageApi.batterySystem

import org.gateway.storage.batterySystem.BatterySystemRepository
import org.gateway.storageApi.batterySystem.converter.BatterySystemConverter
import org.gateway.storageApi.batterySystem.dto.BatteryParameterSummaryDto
import org.gateway.storageApi.batterySystem.dto.BatterySystemDto
import org.gateway.utils.battery.enums.SystemStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

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

    fun findById(id: String): BatterySystemDto {
        id.ifEmpty { throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parameters", null) }
        return repository
            .findById(id = id)
            .let { BatterySystemConverter.convert(entity = it) }
    }

    fun findByManufacturerAndSerialNumber(manufacturer: String, serialNumber: String): BatterySystemDto {
        if (manufacturer.isEmpty() && serialNumber.isEmpty())
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parameters", null)

        return repository
            .findBySerialNumberAndManufacturer(serialNumber = serialNumber, manufacturer = manufacturer)
            .let { if (it.isPresent) BatterySystemConverter.convert(entity = it.get()) else null }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "System not found", null)
    }

    fun findAll(): List<BatterySystemDto> = repository
        .findAll()
        .map { BatterySystemConverter.convert(entity = it) }

    fun findAllByStatus(status: List<SystemStatus>): List<BatterySystemDto> = repository
        .findAllByStatus(status = status)
        .map { BatterySystemConverter.convert(entity = it) }
}
