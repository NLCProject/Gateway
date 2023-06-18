package org.gateway.storage.batterySystem

import org.gateway.storage.batterySystem.interfaces.IBatterySystemRepository
import org.gateway.storage.framework.Repository
import org.gateway.utils.battery.enums.SystemStatus
import org.gateway.websocketInternalApi.InternalWebsocketSessionSender
import org.gateway.websocketInternalApi.messages.SystemRegistered
import org.gateway.websocketInternalApi.messages.SystemStatusChanged
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*

@Service
class BatterySystemRepository @Autowired constructor(
    private val repository: IBatterySystemRepository,
    private val sessionSender: InternalWebsocketSessionSender
) : Repository<BatterySystemEntity>(repository = repository) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun addSystemIfNotExisting(manufacturer: String, serialNumber: String) {
        val optional = findBySerialNumberAndManufacturer(serialNumber = serialNumber, manufacturer = manufacturer)
        if (optional.isPresent)
            return changeStatusInternal(system = optional.get(), newStatus = SystemStatus.ONLINE)

        val entity = BatterySystemEntity().apply {
            this.manufacturer = manufacturer
            this.serialNumber = serialNumber
            this.status = SystemStatus.ONLINE
        }

        logger.info("Saving new system with serial number '$serialNumber'")
        this.save(entity)

        SystemRegistered(serialNumber = serialNumber, manufacturer = manufacturer)
            .apply { sessionSender.sendMessage(message = this) }
    }

    fun changeStatus(manufacturer: String, serialNumber: String, newStatus: SystemStatus) {
        val optional = findBySerialNumberAndManufacturer(serialNumber = serialNumber, manufacturer = manufacturer)
        if (!optional.isPresent)
            return logger.warn("System with serial number '$serialNumber' is unknown")

        changeStatusInternal(system = optional.get(), newStatus = newStatus)
    }

    fun findBySerialNumberAndManufacturer(serialNumber: String, manufacturer: String): Optional<BatterySystemEntity> =
        repository.findBySerialNumberAndManufacturer(
            serialNumber = serialNumber,
            manufacturer = manufacturer
        )

    fun findAllByStatus(status: List<SystemStatus>): List<BatterySystemEntity> =
        repository.findAllByStatusIn(status = status)

    private fun updateModifiedTimestamp(system: BatterySystemEntity) {
        logger.trace("Updating modified timestamp")
        system.dateTimeLastModified = ZonedDateTime.now()
        system.timestampLastModified = System.currentTimeMillis()
        this.save(system)
    }

    private fun changeStatusInternal(system: BatterySystemEntity, newStatus: SystemStatus) {
        if (system.status == newStatus)
            return updateModifiedTimestamp(system = system)

        logger.info("Changing status of system with serial number '${system.serialNumber}' to '$newStatus'")
        system.status = newStatus
        system.dateTimeLastModified = ZonedDateTime.now()
        system.timestampLastModified = System.currentTimeMillis()
        this.save(system)

        SystemStatusChanged(serialNumber = system.serialNumber, manufacturer = system.manufacturer, status = newStatus)
            .apply { sessionSender.sendMessage(message = this) }
    }
}
