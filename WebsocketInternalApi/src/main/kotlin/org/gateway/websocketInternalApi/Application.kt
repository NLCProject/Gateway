package org.gateway.websocketInternalApi

import io.ktor.server.application.*
import org.gateway.websocketApi.plugins.configurePublicSockets
import org.gateway.websocketInternalApi.plugins.configureInternalSockets

fun Application.module() {
    configureInternalSockets()
    configurePublicSockets()
}
