package org.gateway.websocketInternalApi.messages

import kotlinx.serialization.Serializable
import org.gateway.websocketInternalApi.messages.helper.BaseMessage
import org.gateway.websocketInternalApi.messages.helper.MessageType

@Serializable
class SystemRegistered(
    val serialNumber: String,
    val manufacturer: String
) : BaseMessage(text = "New system registered", type = MessageType.SystemRegistered)
