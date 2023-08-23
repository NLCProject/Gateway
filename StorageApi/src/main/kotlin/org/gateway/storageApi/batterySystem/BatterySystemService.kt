package org.gateway.storageApi.batterySystem

import org.gateway.storage.batterySystem.BatterySystemEntity
import org.gateway.storage.batterySystem.BatterySystemRepository
import org.gateway.storage.consumerGroup.ConsumerGroupRepository
import org.gateway.storageApi.batterySystem.converter.BatterySystemConverter
import org.gateway.storageApi.batterySystem.dto.BatterySystemDto
import org.gateway.utils.battery.enums.SystemStatus
import org.gateway.websocketInternalApi.InternalWebsocketSessionSender
import org.gateway.websocketInternalApi.messages.ConsumerGroupsChanged
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class BatterySystemService @Autowired constructor(
    private val repository: BatterySystemRepository,
    private val sessionSender: InternalWebsocketSessionSender,
    private val consumerGroupRepository: ConsumerGroupRepository
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun addSystemIfNotExisting(manufacturer: String, serialNumber: String): BatterySystemEntity =
        repository.addSystemIfNotExisting(manufacturer = manufacturer, serialNumber = serialNumber)

    fun changeStatus(manufacturer: String, serialNumber: String, newStatus: SystemStatus) {
        repository.changeStatus(manufacturer = manufacturer, serialNumber = serialNumber, newStatus = newStatus)
    }

    fun moveToGroup(systemId: String, groupId: String) {
        logger.info("Moving system ID '$systemId' to group ID '$groupId'")
        val system = repository.findById(systemId)
        system.group = consumerGroupRepository.findById(groupId)
        repository.save(system)
        sessionSender.sendMessage(ConsumerGroupsChanged())
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
