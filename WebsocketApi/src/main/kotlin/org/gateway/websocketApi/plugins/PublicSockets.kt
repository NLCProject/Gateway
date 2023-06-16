package org.gateway.websocketApi.plugins

import io.ktor.websocket.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import org.gateway.websocketApi.session.PublicSessionHandler
import org.slf4j.LoggerFactory

fun Application.configurePublicSockets() {
    val logger = LoggerFactory.getLogger(this::class.java)

    routing {
        webSocket("/api/public") {
            try {
                PublicSessionHandler.storeSession(session = this)
                send("Connection successful")

                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    send(receivedText)
                }
            } catch (exception: ClosedReceiveChannelException) {
                logger.info("Public websocket channel closed")
            } catch (exception: Exception) {
                logger.error("Error while handling websocket event", exception)
            } finally {
                PublicSessionHandler.removeSession(session = this)
            }
        }
    }
}
