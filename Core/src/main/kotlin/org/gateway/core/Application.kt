package org.gateway.core

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
    io.ktor.server.netty.EngineMain.main(args)
    // https://ktor.io/docs/creating-web-socket-chat.html
    // https://github.com/ktorio/ktor-documentation/blob/2.3.0/codeSnippets/snippets/tutorial-websockets-server/src/main/kotlin/com/example/Connection.kt
}
