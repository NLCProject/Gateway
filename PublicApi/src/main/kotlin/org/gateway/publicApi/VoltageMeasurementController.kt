package org.gateway.publicApi

import org.gateway.storageApi.batterySystem.BatterySystemService
import org.gateway.utils.controllers.ControllerCallback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo

@Controller
class VoltageMeasurementController @Autowired constructor(
    private val batterySystemService: BatterySystemService
) {

    @MessageMapping("/measurement/voltage")
    @SendTo("/topic/measurement/voltage")
    fun findAll(): ResponseEntity<*> = ControllerCallback.getOperation { batterySystemService.findAll() }
}
