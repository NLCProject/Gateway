package org.gateway.websocketInternalApi.messages

import kotlinx.serialization.Serializable
import org.gateway.utils.battery.enums.SystemStatus
import org.gateway.websocketInternalApi.messages.helper.BaseMessage
import org.gateway.websocketInternalApi.messages.helper.MessageType

@Serializable
class SystemStatusChanged(
    val serialNumber: String,
    val manufacturer: String,
    val status: SystemStatus
) : BaseMessage(text = "Status changed of system", type = MessageType.SystemStatusChanged)
