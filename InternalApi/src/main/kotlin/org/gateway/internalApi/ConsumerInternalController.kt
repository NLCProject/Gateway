package org.gateway.internalApi

import org.gateway.storage.consumerGroup.WiringMode
import org.gateway.storageApi.consumerGroup.ConsumerGroupService
import org.gateway.utils.controller.ControllerCallback
import org.gateway.utils.controller.CrossOriginData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Controller
@Transactional
@RequestMapping(path = ["/api/internal/consumer"])
@CrossOrigin(origins = [CrossOriginData.origins], allowedHeaders = [CrossOriginData.allowedHeaders])
class ConsumerInternalController @Autowired constructor(
    private val consumerGroupService: ConsumerGroupService
) {

    @GetMapping(value = ["/findAll"])
    fun findAll(): ResponseEntity<*> = ControllerCallback.getOperation {
        consumerGroupService
            .findAll()
            .sortedBy { it.id }
    }

    @PostMapping(value = ["/save"])
    fun save(@RequestParam groupId: String, @RequestParam name: String, @RequestParam mode: WiringMode): ResponseEntity<*> =
        ControllerCallback.postOperation {
            consumerGroupService.saveInternal(groupId = groupId, name = name, mode = mode)
        }
}
