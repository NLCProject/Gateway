package org.gateway.adapter.virtualPowerPlant

import org.gateway.utils.SystemConfiguration
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class VirtualPowerPlantAdapter {

    @Value("\${server.port}")
    var serverPort: String = ""

    @Value("\${server.host}")
    var serverHost: String = ""

    @Value("\${adapter.vpp.url}")
    var url: String = ""

    @Value("\${adapter.vpp.port}")
    var vppPort: String = ""

    @Value("\${adapter.vpp.host}")
    var vppHost: String = ""

    @Value("\${adapter.vpp.enabled}")
    var enabled: String = ""

    private val logger = LoggerFactory.getLogger(this::class.java)

    @EventListener(ApplicationReadyEvent::class)
    fun connect() {
        try {
            if (!enabled.toBoolean())
                return logger.info("Virtual power plant adapter disabled")

            val validUrl = url.removePrefix("/")
            val validVppPort = vppPort.toIntOrNull() ?: return
            val validServerPort = serverPort.toIntOrNull() ?: return

            if (validServerPort <= 0 || validVppPort <= 0 || validUrl.isEmpty() || invalidConfiguration())
                return logger.warn("Invalid VPP adapter configuration")

            logger.info("Starting virtual power plant adapter")
            val dto = VirtualPowerPlantAdapterDto().apply {
                this.websocketPort = 8081
                this.gatewayHost = serverHost
                this.gatewayUrl = "api/public"
                this.gatewayPort = validServerPort
                this.serialNumber = SystemConfiguration.serialNumber
            }

            Thread {
                Thread.sleep(15_000)
                val uri = "http://$vppHost:$validVppPort/$validUrl"
                logger.info("Connecting to VPP via URL '$uri'")
                val response = RestTemplate().postForEntity(uri, dto, String::class.java)
                logger.info("Response code from VPP | ${response.statusCode}")
            }.start()
        } catch (exception: Exception) {
            logger.error("Error while connecting to VPP | ${exception.message}")
        }
    }

    private fun invalidConfiguration(): Boolean = vppHost.isEmpty() || serverHost.isEmpty()
}
