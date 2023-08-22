package org.gateway.storageApi.consumerGroup.dto

import org.gateway.storage.consumerGroup.ConsumerMode
import org.gateway.storage.consumerGroup.WiringMode
import org.gateway.storageApi.batterySystem.dto.BatterySystemDto
import org.gateway.storageApi.dto.Dto

class ConsumerGroupDto : Dto() {
    lateinit var name: String
    var voltage: Double = 0.0
    var standard: Boolean = false
    lateinit var mode: ConsumerMode
    lateinit var wiring: WiringMode
    var systems: List<BatterySystemDto> = emptyList()
}
