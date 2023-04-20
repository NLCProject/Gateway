package org.gateway.storage.framework

import java.util.*

interface RepositoryBaseOperations<ENTITY : DistributedEntity> {

    fun findAllByIds(ids: List<String>): List<ENTITY>

    fun existsById(id: String): Boolean

    fun findAll(): List<ENTITY>

    fun findByIdOptional(id: String): Optional<ENTITY>

    fun findById(id: String): ENTITY

    fun deleteById(id: String)

    fun deleteByIdIfExist(id: String)

    fun deleteAll()

    fun save(entity: ENTITY): ENTITY
}
