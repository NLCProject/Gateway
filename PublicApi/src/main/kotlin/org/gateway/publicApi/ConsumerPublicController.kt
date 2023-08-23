package org.gateway.publicApi

import org.gateway.storage.consumerGroup.ConsumerMode
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
@RequestMapping(path = ["/api/public/consumer"])
@CrossOrigin(origins = [CrossOriginData.origins], allowedHeaders = [CrossOriginData.allowedHeaders])
class ConsumerPublicController @Autowired constructor(
    private val consumerGroupService: ConsumerGroupService
) {

    @GetMapping(value = ["/findAll"])
    fun findAll(): ResponseEntity<*> = ControllerCallback.getOperation {
        consumerGroupService
            .findAll()
            .sortedBy { it.id }
    }

    @GetMapping(value = ["/findById"])
    fun findById(@RequestParam groupId: String): ResponseEntity<*> = ControllerCallback.getOperation {
        consumerGroupService.findById(groupId)
    }

    @PostMapping(value = ["/changeConsumerMode"])
    fun changeConsumerMode(@RequestParam groupId: String, @RequestParam mode: ConsumerMode): ResponseEntity<*> =
        ControllerCallback.postOperation {
            consumerGroupService.changeConsumerMode(groupId = groupId, mode = mode)
        }
}
