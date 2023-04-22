package org.gateway.websocketInternalApi.messages

import kotlinx.serialization.Serializable
import org.gateway.websocketInternalApi.messages.helper.BaseMessage
import org.gateway.websocketInternalApi.messages.helper.MessageType

@Serializable
class SystemDetected : BaseMessage(text = "New system detected", type = MessageType.SystemDetected)
