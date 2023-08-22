package org.gateway.storage.consumerGroup

import org.gateway.storage.consumerGroup.interfaces.IConsumerGroupRepository
import org.gateway.storage.framework.Repository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConsumerGroupRepository @Autowired constructor(
    private val repository: IConsumerGroupRepository
) : Repository<ConsumerGroupEntity>(repository = repository) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun findOrCreateDefaultGroup(): ConsumerGroupEntity {
        val optional = repository.findByStandardTrue()
        if (optional.isPresent) {
            return optional.get()
        }

        logger.info("Creating default consuming group")
        val group = ConsumerGroupEntity().apply {
            this.standard = true
            this.name = "Standard"
            this.mode = ConsumerMode.None
            this.wiring = WiringMode.Unknown
        }

        return repository.save(group)
    }
}
