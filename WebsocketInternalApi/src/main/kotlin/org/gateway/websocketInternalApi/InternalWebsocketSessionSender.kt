package org.gateway.websocketInternalApi

import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import org.gateway.utils.serialzation.JsonSerialization
import org.gateway.websocketApi.session.PublicSessionHandler
import org.gateway.websocketInternalApi.dto.WebsocketMessage
import org.gateway.websocketInternalApi.session.InternalSessionHandler
import org.gateway.websocketInternalApi.messages.SystemDetected
import org.gateway.websocketInternalApi.messages.SystemRegistered
import org.gateway.websocketInternalApi.messages.SystemStatusChanged
import org.gateway.websocketInternalApi.messages.VoltageMeasurement
import org.gateway.websocketInternalApi.messages.helper.MessageType
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class InternalWebsocketSessionSender : JsonSerialization() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun sendMessage(message: SystemDetected) {
        WebsocketMessage(type = MessageType.SystemDetected).apply { sendToInternalSessions(message = this) }
        sendToPublicSessions(serialized = encode(message))
    }

    fun sendMessage(message: SystemRegistered) {
        WebsocketMessage(type = MessageType.SystemRegistered).apply { sendToInternalSessions(message = this) }
        sendToPublicSessions(serialized = encode(message))
    }

    fun sendMessage(message: SystemStatusChanged) {
        WebsocketMessage(
            data = message.status.toString(),
            manufacturer = message.manufacturer,
            serialNumber = message.serialNumber,
            type = MessageType.SystemStatusChanged
        ).apply { sendToInternalSessions(message = this) }

        sendToPublicSessions(serialized = encode(message))
    }

    fun sendMessage(message: VoltageMeasurement) {
        WebsocketMessage(
            data = message.value.toString(),
            manufacturer = message.manufacturer,
            serialNumber = message.serialNumber,
            type = MessageType.VoltageMeasurement
        ).apply { sendToInternalSessions(message = this) }

        sendToPublicSessions(serialized = encode(message))
    }

    private fun sendToInternalSessions(message: WebsocketMessage) {
        val serialized = encode(message)
        runBlocking {
            logger.trace("Sending internal websocket event")

            InternalSessionHandler
                .getAllSessions()
                .forEach { it.outgoing.send(Frame.Text(serialized)) }
        }
    }

    private fun sendToPublicSessions(serialized: String) {
        runBlocking {
            logger.trace("Sending public websocket event")

            PublicSessionHandler
                .getAllSessions()
                .forEach { it.outgoing.send(Frame.Text(serialized)) }
        }
    }
}
