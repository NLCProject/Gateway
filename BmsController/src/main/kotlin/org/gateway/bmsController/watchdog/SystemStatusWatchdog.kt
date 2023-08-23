package org.gateway.bmsController.watchdog

import org.gateway.storageApi.batterySystem.BatterySystemService
import org.gateway.storageApi.batterySystem.dto.BatterySystemDto
import org.gateway.utils.battery.enums.SystemStatus
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class SystemStatusWatchdog @Autowired constructor(
    private val batterySystemService: BatterySystemService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(cron = "*/2 * * * * *")
    fun verifySystemStatus() {
        val now = System.currentTimeMillis()
        logger.debug("Verifying system status")

        batterySystemService
            .findAllByStatus(status = listOf(SystemStatus.ONLINE, SystemStatus.STANDBY))
            .toList()
            .forEach {
                when (it.status) {
                    SystemStatus.ONLINE -> handleSystem(system = it, now = now, newStatus = SystemStatus.STANDBY)
                    SystemStatus.STANDBY -> handleSystem(system = it, now = now, newStatus = SystemStatus.OFFLINE)
                    else -> throw Exception("Invalid system status '${it.status}'")
                }
            }
    }

    private fun handleSystem(system: BatterySystemDto, now: Long, newStatus: SystemStatus) {
        val exceeded = isExceeded(system = system, now = now)
        if (exceeded) {
            logger.warn("System with serial number '${system.serialNumber}' is inactive. New status is '$newStatus'")
            batterySystemService.changeStatus(
                newStatus = newStatus,
                manufacturer = system.manufacturer,
                serialNumber = system.serialNumber
            )
        }
    }

    private fun isExceeded(system: BatterySystemDto, now: Long): Boolean {
        val dateTime = system.timestampLastModified ?: system.timestampCreated
        val threshold = dateTime + system.status.timeoutInSeconds.toLong().times(1000)
        return threshold < now
    }
}
