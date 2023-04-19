package org.gateway.connector.events

import org.gateway.connector.dto.SerialDataResponse
import org.springframework.context.ApplicationEvent

class SendSerialResponseEvent(source: Any, dto: SerialDataResponse) : ApplicationEvent(source)
