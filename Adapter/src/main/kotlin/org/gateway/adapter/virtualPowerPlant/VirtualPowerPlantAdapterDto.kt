package org.gateway.adapter.virtualPowerPlant

import kotlinx.serialization.Serializable

@Serializable
class VirtualPowerPlantAdapterDto {
    var gatewayPort: Int = 0
    var websocketPort: Int = 0
    lateinit var gatewayUrl: String
    lateinit var gatewayHost: String
    lateinit var serialNumber: String
}
