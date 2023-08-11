package org.gateway.simulator

import org.gateway.bmsController.connector.dto.SerialDataCommand
import org.gateway.bmsController.connector.dto.SerialDataRequest
import org.gateway.bmsController.connector.interfaces.ISerialDataHandler
import org.gateway.simulator.configuration.VirtualBatteries
import org.gateway.websocketInternalApi.InternalWebsocketSessionSender
import org.gateway.websocketInternalApi.messages.SystemDetected
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class VirtualClientSimulator @Autowired constructor(
    private val serialDataHandler: ISerialDataHandler,
    private val sessionSender: InternalWebsocketSessionSender
) {

    private var isRunning = false
    private val logger = LoggerFactory.getLogger(this::class.java)

    @EventListener(ApplicationReadyEvent::class)
    fun loadVirtualClients() {
        try {
            logger.info("Loading virtual clients")
            VirtualBatteries
                .clients
                .onEach { sessionSender.sendMessage(message = SystemDetected()) }
                .apply { if (this.isNotEmpty()) isRunning = true }
        } catch (exception: Exception) {
            logger.error("Error while starting simulator | ${exception.message}")
        }
    }

    @Scheduled(cron = "* * * * * *")
    fun simulate() {
        try {
            if (!isRunning)
                return

            VirtualBatteries
                .clients
                .forEach {
                    val min = it.voltage * it.lowerDispersion
                    val max = it.voltage * it.upperDispersion
                    val value = Random.nextDouble(from = min, until = max)

                    val dto = SerialDataRequest().apply {
                        this.data = value.toString()
                        this.manufacturer = it.manufacturer
                        this.serialNumber = it.serialNumber
                        this.command = SerialDataCommand.Measurement
                    }

                    serialDataHandler.handleData(dto = dto)
                }
        } catch (exception: Exception) {
            logger.error("Error while running simulator | ${exception.message}")
        }
    }
}
