package org.gateway.storage.framework

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository

@NoRepositoryBean
interface ICrudlRepository<ENTITY : DistributedEntity> : PagingAndSortingRepository<ENTITY, String>
