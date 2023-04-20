package org.gateway.storage.framework

import org.gateway.utils.validation.ValidationUtil
import java.util.*

abstract class Repository<ENTITY : DistributedEntity>(
    private val repository: ICrudlRepository<ENTITY>,
) : RepositoryBaseOperations<ENTITY> {

    override fun findAllByIds(ids: List<String>): List<ENTITY> = repository.findAllById(ids).toList()

    override fun existsById(id: String): Boolean = repository.existsById(id)

    override fun findAll(): List<ENTITY> = repository.findAll().toList()

    override fun findByIdOptional(id: String): Optional<ENTITY> {
        if (id.isEmpty())
            return Optional.empty()

        return repository.findById(id)
    }

    override fun findById(id: String): ENTITY = findByIdOptional(id = id).get()

    override fun deleteById(id: String) = repository.deleteById(id)

    override fun deleteByIdIfExist(id: String) {
        val exist = existsById(id = id)
        if (exist)
            repository.deleteById(id)
    }

    override fun deleteAll() = repository.deleteAll()

    override fun save(entity: ENTITY): ENTITY {
        ValidationUtil.verifyIfEmptyAndThrow(value = entity.id)
        return repository.save(entity)
    }
}
