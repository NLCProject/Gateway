package org.gateway.storage.framework

import org.gateway.utils.serialzation.JsonSerialization
import org.gateway.utils.validation.ValidationUtil

abstract class Repository<ENTITY : DistributedEntity>(
    private val repository: ICrudlRepository<ENTITY>,
) : RepositoryBaseOperations<ENTITY>, JsonSerialization() {

    override fun findAll(): List<ENTITY> = repository.findAll().toList()

    override fun findById(id: String): ENTITY = repository.findById(id).get()

    override fun save(entity: ENTITY): ENTITY {
        ValidationUtil.verifyIfEmptyAndThrow(value = entity.id)
        return repository.save(entity)
    }
}
