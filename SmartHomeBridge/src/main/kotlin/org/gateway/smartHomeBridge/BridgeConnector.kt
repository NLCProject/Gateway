package org.gateway.smartHomeBridge

import org.gateway.storageApi.batterySystem.dto.BatteryParameterSummaryDto
import org.gateway.storageApi.systemParameter.SystemParameterService
import org.gateway.utils.controller.CrossOriginData
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@Transactional
@RequestMapping(path = ["/api/smartHome/myGateway"])
@CrossOrigin(origins = [CrossOriginData.origins], allowedHeaders = [CrossOriginData.allowedHeaders])
class BridgeConnector @Autowired constructor(
    private val systemParameterService: SystemParameterService
) {

    @Value("\${smarthome.bridge.enabled}")
    var enabled: String = ""

    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping(value = ["/getSystems"])
    fun getSystems(): ResponseEntity<BatteryParameterSummaryDto> {
        logger.info("Requesting system information by smart home")
        if (!enabled.toBooleanStrict())
            return ResponseEntity(HttpStatus.OK)

        val summary = systemParameterService.getSummary()
        logger.info("Number of systems '${summary.systems.size}' | Voltage '${summary.voltage}V' | " +
            "All voltages '${summary.systems.joinToString(" | ") { "${it.measuredValue}V" }}")

        return ResponseEntity(summary, HttpStatus.OK)
    }
}
