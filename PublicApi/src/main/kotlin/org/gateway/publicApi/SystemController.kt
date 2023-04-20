package org.gateway.publicApi

import org.gateway.storageApi.batterySystem.BatterySystemService
import org.gateway.utils.controllers.ControllerCallback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.gateway.utils.controllers.CrossOriginData

@Controller
@Transactional
@RequestMapping(path = ["/system"])
@CrossOrigin(origins = [CrossOriginData.origins], allowedHeaders = [CrossOriginData.allowedHeaders])
class SystemController @Autowired constructor(
    private val batterySystemService: BatterySystemService
) {

    @GetMapping(value = ["/findAll"])
    fun findAll(): ResponseEntity<*> = ControllerCallback.getOperation { batterySystemService.findAll() }

    @GetMapping(value = ["/findOne"])
    fun findOne(
        @RequestParam manufacturer: String,
        @RequestParam serialNumber: String
    ): ResponseEntity<*> = ControllerCallback.getOperation {
        batterySystemService.findOne(manufacturer = manufacturer, serialNumber = serialNumber) ?: ""
    }
}
