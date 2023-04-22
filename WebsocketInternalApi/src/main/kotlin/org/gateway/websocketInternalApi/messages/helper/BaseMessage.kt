package org.gateway.websocketInternalApi.messages.helper

import kotlinx.serialization.Serializable

@Serializable
abstract class BaseMessage(val text: String, val type: MessageType)
