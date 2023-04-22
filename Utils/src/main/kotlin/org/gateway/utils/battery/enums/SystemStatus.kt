package org.gateway.utils.battery.enums

import kotlinx.serialization.Serializable

@Serializable
enum class SystemStatus(var timeoutInSeconds: Int = 0) {
    UNKNOWN,
    ONLINE(timeoutInSeconds = 5),
    STANDBY(timeoutInSeconds = 20),
    OFFLINE
}
