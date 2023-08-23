package org.gateway.storage.framework

import org.gateway.utils.ids.Ids
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class DistributedEntity {

    @Id
    @Column
    var id: String = Ids.getRandomId()

    @Column(nullable = false)
    var timestampCreated: Long = System.currentTimeMillis()

    @Column
    var timestampLastModified: Long? = null
}
