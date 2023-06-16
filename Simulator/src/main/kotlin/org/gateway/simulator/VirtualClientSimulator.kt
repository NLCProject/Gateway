package org.gateway.simulator

import org.gateway.bmsController.connector.dto.SerialDataCommand
import org.gateway.bmsController.connector.dto.SerialDataRequest
import org.gateway.bmsController.connector.interfaces.ISerialDataHandler
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
    private var clients = mutableListOf<VirtualClient>()
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val manufacturers = listOf("Joy-It", "Varta", "Duracell", "LG Energy Solution", "Panasonic", "Samsung SDI")

    @Value("\${gateway.bms.numberOfVirtualClients}")
    var numberOfVirtualClients: String = ""

    @EventListener(ApplicationReadyEvent::class)
    fun loadVirtualClients() {
        logger.info("Loading virtual clients")
        val count = numberOfVirtualClients.toIntOrNull() ?: 0
        clients = (1..count)
            .map {
                VirtualClient(
                    manufacturer = manufacturers.random(),
                    baseValue = Random.nextDouble(from = 0.3, until = 9.1)
                )
            }
            .onEach { sessionSender.sendMessage(message = SystemDetected()) }
            .toMutableList()
            .apply {
                if (this.size > 0)
                    isRunning = true
            }
    }

    @Scheduled(cron = "* * * * * *")
    fun simulate() {
        if (!isRunning)
            return

        logger.trace("Running simulator for '${clients.size}' virtual clients")

        clients
            .forEach {
                val min = it.baseValue * 0.95
                val max = it.baseValue * 1.05
                val value = Random.nextDouble(from = min, until = max)

                val dto = SerialDataRequest().apply {
                    this.data = value.toString()
                    this.manufacturer = it.manufacturer
                    this.serialNumber = it.serialNumber
                    this.command = SerialDataCommand.Measurement
                }

                serialDataHandler.handleData(dto = dto)
            }
    }
}
