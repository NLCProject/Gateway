package org.gateway.core

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["org.gateway.bmsController"])
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
