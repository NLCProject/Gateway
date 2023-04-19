package org.gateway.bmsController.connector.events

import org.gateway.bmsController.connector.dto.SerialDataResponse
import org.springframework.context.ApplicationEvent

class SendSerialResponseEvent(source: Any, dto: SerialDataResponse) : ApplicationEvent(source)
