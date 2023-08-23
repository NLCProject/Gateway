package org.gateway.websocketInternalApi.messages

import kotlinx.serialization.Serializable
import org.gateway.websocketInternalApi.messages.helper.BaseMessage
import org.gateway.websocketInternalApi.messages.helper.MessageType

@Serializable
class ConsumerGroupsChanged : BaseMessage(text = "Consumer groups changed", type = MessageType.ConsumerGroupsChanged)
