package org.gateway.websocketInternalApi

import io.ktor.server.application.*
import org.gateway.websocketApi.plugins.configureInternalSockets
import org.gateway.websocketInternalApi.plugins.configurePublicSockets

fun Application.module() {
    configurePublicSockets()
    configureInternalSockets()
}
