package org.gateway.storage.batterySystem.interfaces

import org.gateway.storage.batterySystem.BatterySystemEntity
import org.gateway.storage.framework.ICrudlRepository
import org.gateway.utils.battery.enums.SystemStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
interface IBatterySystemRepository : ICrudlRepository<BatterySystemEntity> {

    fun findBySerialNumberAndManufacturer(serialNumber: String, manufacturer: String): Optional<BatterySystemEntity>

    fun findAllByStatusIn(status: List<SystemStatus>): List<BatterySystemEntity>
}
