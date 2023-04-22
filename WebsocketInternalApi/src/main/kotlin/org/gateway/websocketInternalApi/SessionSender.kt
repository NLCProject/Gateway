package org.gateway.websocketInternalApi

import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import org.gateway.websocketApi.session.SessionHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SessionSender {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun sendMessage(message: String) {
        runBlocking {
            logger.info("Sending websocket event")

            SessionHandler
                .getAllSessions()
                .forEach { it.outgoing.send(Frame.Text(message)) }
        }
    }
}
