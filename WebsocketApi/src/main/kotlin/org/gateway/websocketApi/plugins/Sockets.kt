package org.gateway.websocketApi.plugins

import io.ktor.websocket.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import org.gateway.websocketApi.session.SessionHandler
import org.slf4j.LoggerFactory
import java.time.*

fun Application.configureSockets() {
    val logger = LoggerFactory.getLogger(this::class.java)

    install(WebSockets) {
        masking = false
        maxFrameSize = Long.MAX_VALUE
        timeout = Duration.ofSeconds(15)
        pingPeriod = Duration.ofSeconds(15)
    }

    routing {
        webSocket("/api") {
            try {
                SessionHandler.storeSession(session = this)
                send("Connection successful")

                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    send("You said: $receivedText")
                }
            } catch (exception: ClosedReceiveChannelException) {
                logger.info("Websocket channel closed")
            } catch (exception: Exception) {
                logger.error("Error while handling websocket event", exception)
            } finally {
                SessionHandler.removeSession(session = this)
            }
        }
    }
}
