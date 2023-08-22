package org.gateway.storageApi.consumerGroup

import org.gateway.storage.consumerGroup.ConsumerGroupRepository
import org.gateway.storageApi.consumerGroup.converter.ConsumerGroupConverter
import org.gateway.storageApi.consumerGroup.dto.ConsumerGroupDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConsumerGroupService @Autowired constructor(
    private val consumerGroupRepository: ConsumerGroupRepository
) {

    fun findAll(): List<ConsumerGroupDto> = consumerGroupRepository
        .findAll()
        .map { ConsumerGroupConverter.convert(it) }
}
