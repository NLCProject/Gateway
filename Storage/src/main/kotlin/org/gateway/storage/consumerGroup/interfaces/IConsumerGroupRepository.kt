package org.gateway.storage.consumerGroup.interfaces

import org.gateway.storage.consumerGroup.ConsumerGroupEntity
import org.gateway.storage.framework.ICrudlRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
interface IConsumerGroupRepository : ICrudlRepository<ConsumerGroupEntity> {

    fun findByStandardTrue(): Optional<ConsumerGroupEntity>
}
