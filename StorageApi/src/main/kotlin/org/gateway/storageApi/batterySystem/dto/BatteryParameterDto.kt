package org.gateway.storageApi.batterySystem.dto

import org.gateway.utils.battery.enums.SystemStatus

class BatteryParameterDto {
    var measuredValue: Double = 0.0
    lateinit var status: SystemStatus
    lateinit var manufacturer: String
    lateinit var serialNumber: String
}
