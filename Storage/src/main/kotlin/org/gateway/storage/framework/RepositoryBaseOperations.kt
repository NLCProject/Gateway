package org.gateway.storage.framework

interface RepositoryBaseOperations<ENTITY : DistributedEntity> {

    fun findAll(): List<ENTITY>

    fun findById(id: String): ENTITY

    fun save(entity: ENTITY): ENTITY
}
