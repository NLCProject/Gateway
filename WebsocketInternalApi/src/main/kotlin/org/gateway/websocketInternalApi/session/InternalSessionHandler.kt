package org.gateway.websocketInternalApi.session

import io.ktor.websocket.*
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.collections.LinkedHashSet

object InternalSessionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val connections = Collections.synchronizedSet<DefaultWebSocketSession?>(LinkedHashSet())

    fun storeSession(session: DefaultWebSocketSession) {
        connections += session
        logger.info("Websocket connection added")
    }

    fun removeSession(session: DefaultWebSocketSession) {
        connections -= session
        logger.info("Websocket connection removed")
    }

    fun getAllSessions(): Set<WebSocketSession> = connections.toSet()
}
