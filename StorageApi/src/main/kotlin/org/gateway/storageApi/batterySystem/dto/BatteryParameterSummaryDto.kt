package org.gateway.storageApi.batterySystem.dto

class BatteryParameterSummaryDto {
    var voltage: Double = 0.0
    lateinit var systems: List<BatteryParameterDto>
}
