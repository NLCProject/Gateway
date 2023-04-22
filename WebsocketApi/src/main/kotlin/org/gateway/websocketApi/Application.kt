package org.gateway.websocketApi

import io.ktor.server.application.*
import org.gateway.websocketApi.plugins.configureSockets

fun Application.module() {
    configureSockets()
}
