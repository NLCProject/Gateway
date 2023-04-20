package org.gateway.utils.battery.enums

enum class SystemStatus(var timeoutInSeconds: Int = 0) {
    ONLINE(timeoutInSeconds = 5),
    STANDBY(timeoutInSeconds = 20),
    OFFLINE
}
