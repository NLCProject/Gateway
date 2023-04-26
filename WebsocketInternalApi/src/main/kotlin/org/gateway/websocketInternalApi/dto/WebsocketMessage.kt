package org.gateway.websocketInternalApi.dto

import kotlinx.serialization.Serializable
import org.gateway.websocketInternalApi.messages.helper.MessageType

@Serializable
class WebsocketMessage(
    val type: MessageType,
    val data: String = "",
    val manufacturer: String = "",
    val serialNumber: String = ""
)
