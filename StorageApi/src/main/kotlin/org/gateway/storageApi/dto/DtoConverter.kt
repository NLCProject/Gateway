package org.gateway.storageApi.dto

import org.gateway.storage.framework.DistributedEntity

object DtoConverter {

    fun convert(entity: DistributedEntity, dto: Dto) {
        dto.apply {
            id = entity.id
            timestampCreated = entity.timestampCreated
            timestampLastModified = entity.timestampLastModified
        }
    }
}
