package org.gateway.websocketInternalApi.messages.helper

import kotlinx.serialization.Serializable
import org.gateway.utils.SystemConfiguration

@Serializable
abstract class BaseMessage(
    val text: String,
    val type: MessageType,
    val gatewaySerialNumber: String = SystemConfiguration.serialNumber
)
