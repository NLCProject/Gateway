package org.gateway.simulator.configuration

import org.gateway.utils.ids.Ids

data class VirtualBattery(
    val voltage: Double,
    val manufacturer: String,
    val lowerDispersion: Double,
    val upperDispersion: Double,
    val serialNumber: String = Ids.getRandomId()
)
