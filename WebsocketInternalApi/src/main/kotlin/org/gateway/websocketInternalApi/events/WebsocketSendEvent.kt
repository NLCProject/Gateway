package org.gateway.websocketInternalApi.events

import org.gateway.websocketInternalApi.messages.helper.BaseMessage
import org.springframework.context.ApplicationEvent

class WebsocketSendEvent(source: Any, val message: BaseMessage) : ApplicationEvent(source)
