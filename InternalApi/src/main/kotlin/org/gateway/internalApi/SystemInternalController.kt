package org.gateway.internalApi

import org.gateway.storageApi.batterySystem.BatterySystemService
import org.gateway.utils.controller.ControllerCallback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.gateway.utils.controller.CrossOriginData

@Controller
@Transactional
@RequestMapping(path = ["/api/internal/system"])
@CrossOrigin(origins = [CrossOriginData.origins], allowedHeaders = [CrossOriginData.allowedHeaders])
class SystemInternalController @Autowired constructor(
    private val batterySystemService: BatterySystemService
) {

    @GetMapping(value = ["/findAll"])
    fun findAll(): ResponseEntity<*> = ControllerCallback.getOperation {
        batterySystemService.findAll()
    }

    @GetMapping(value = ["/findById"])
    fun findById(
        @RequestParam id: String
    ): ResponseEntity<*> = ControllerCallback.getOperation {
        batterySystemService.findById(id = id)
    }
}
