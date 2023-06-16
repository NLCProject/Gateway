package org.gateway.websocketInternalApi.plugins

import io.ktor.websocket.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import org.gateway.websocketInternalApi.session.InternalSessionHandler
import org.slf4j.LoggerFactory
import java.time.*

fun Application.configureInternalSockets() {
    val logger = LoggerFactory.getLogger(this::class.java)

    install(WebSockets) {
        masking = false
        maxFrameSize = Long.MAX_VALUE
        timeout = Duration.ofSeconds(15)
        pingPeriod = Duration.ofSeconds(15)
    }

    routing {
        webSocket("/api/internal") {
            try {
                InternalSessionHandler.storeSession(session = this)

                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    send(receivedText)
                }
            } catch (exception: ClosedReceiveChannelException) {
                logger.info("Internal websocket channel closed")
            } catch (exception: Exception) {
                logger.error("Error while handling websocket event", exception)
            } finally {
                InternalSessionHandler.removeSession(session = this)
            }
        }
    }
}
