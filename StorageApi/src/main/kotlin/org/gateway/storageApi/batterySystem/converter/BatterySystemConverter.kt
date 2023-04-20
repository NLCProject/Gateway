package org.gateway.storageApi.batterySystem.converter

import org.gateway.storage.batterySystem.BatterySystemEntity
import org.gateway.storageApi.batterySystem.dto.BatterySystemDto
import org.gateway.storageApi.dto.DtoConverter

object BatterySystemConverter {

    fun convert(entity: BatterySystemEntity): BatterySystemDto = BatterySystemDto().apply {
        status = entity.status
        manufacturer = entity.manufacturer
        serialNumber = entity.serialNumber
        DtoConverter.convert(entity = entity, dto = this)
    }
}
