package org.gateway.websocketInternalApi.messages.helper

import kotlinx.serialization.Serializable

@Serializable
enum class MessageType {
    SystemDetected,
    SystemRegistered,
    VoltageMeasurement,
    SystemStatusChanged,
    ConsumerGroupsChanged
}
