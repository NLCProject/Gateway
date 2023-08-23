package org.gateway.internalApi

import org.gateway.storageApi.batterySystem.BatterySystemService
import org.gateway.utils.controller.ControllerCallback
import org.gateway.utils.controller.CrossOriginData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Controller
@Transactional
@RequestMapping(path = ["/api/internal/system"])
@CrossOrigin(origins = [CrossOriginData.origins], allowedHeaders = [CrossOriginData.allowedHeaders])
class SystemInternalController @Autowired constructor(
    private val batterySystemService: BatterySystemService
) {

    @GetMapping(value = ["/findAll"])
    fun findAll(): ResponseEntity<*> = ControllerCallback.getOperation {
        batterySystemService
            .findAll()
            .sortedBy { it.id }
    }

    @PostMapping(value = ["/moveToGroup"])
    fun moveToGroup(@RequestParam systemId: String, groupId: String): ResponseEntity<*> =
        ControllerCallback.postOperation {
            batterySystemService.moveToGroup(systemId = systemId, groupId = groupId)
        }
}
