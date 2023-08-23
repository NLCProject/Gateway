package org.gateway.storageApi.batterySystem.dto

import org.gateway.storageApi.consumerGroup.dto.ConsumerGroupDto
import org.gateway.storageApi.dto.Dto
import org.gateway.utils.battery.enums.SystemStatus

class BatterySystemDto : Dto() {
    var voltage: Double = 0.0
    lateinit var status: SystemStatus
    lateinit var manufacturer: String
    lateinit var serialNumber: String
    var group: ConsumerGroupDto? = null
}
