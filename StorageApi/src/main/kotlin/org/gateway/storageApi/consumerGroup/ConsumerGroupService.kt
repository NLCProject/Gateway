package org.gateway.storageApi.consumerGroup

import org.gateway.storage.consumerGroup.ConsumerGroupEntity
import org.gateway.storage.consumerGroup.ConsumerGroupRepository
import org.gateway.storage.consumerGroup.ConsumerMode
import org.gateway.storage.consumerGroup.WiringMode
import org.gateway.storageApi.consumerGroup.converter.ConsumerGroupConverter
import org.gateway.storageApi.consumerGroup.dto.ConsumerGroupDto
import org.gateway.websocketInternalApi.InternalWebsocketSessionSender
import org.gateway.websocketInternalApi.messages.ConsumerGroupsChanged
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConsumerGroupService @Autowired constructor(
    private val sessionSender: InternalWebsocketSessionSender,
    private val consumerGroupRepository: ConsumerGroupRepository
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun findAll(): List<ConsumerGroupDto> = consumerGroupRepository
        .findAll()
        .map { ConsumerGroupConverter.convert(it) }

    fun findById(groupId: String): ConsumerGroupDto = consumerGroupRepository
        .findById(groupId)
        .let { ConsumerGroupConverter.convert(it) }

    fun changeConsumerMode(groupId: String, mode: ConsumerMode) {
        logger.info("Changing consumer mode from group ID '$groupId' to '$mode'")
        val group = consumerGroupRepository.findById(groupId)
        group.mode = mode
        consumerGroupRepository.save(group)
        sessionSender.sendMessage(ConsumerGroupsChanged())
    }

    fun saveInternal(groupId: String, name: String, mode: WiringMode) {
        logger.info("Saving consumer group ID '$groupId' to '$mode'")
        val group = if (groupId.isEmpty()) ConsumerGroupEntity() else consumerGroupRepository.findById(groupId)
        group.name = name
        group.wiring = mode
        consumerGroupRepository.save(group)
        sessionSender.sendMessage(ConsumerGroupsChanged())
    }
}
