package org.gateway.websocketInternalApi.messages

import kotlinx.serialization.Serializable
import org.gateway.websocketInternalApi.messages.helper.BaseMessage
import org.gateway.websocketInternalApi.messages.helper.MessageType

@Serializable
class VoltageMeasurement(
    val value: Double,
    val serialNumber: String,
    val manufacturer: String
) : BaseMessage(text = "Voltage measured", type = MessageType.VoltageMeasurement)
