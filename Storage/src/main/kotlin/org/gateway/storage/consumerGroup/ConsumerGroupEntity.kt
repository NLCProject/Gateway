package org.gateway.storage.consumerGroup

import org.gateway.storage.batterySystem.BatterySystemEntity
import org.gateway.storage.framework.DistributedEntity
import javax.persistence.*

@Entity
@Table(name = "consumer_group")
class ConsumerGroupEntity : DistributedEntity() {

    @Enumerated(value = EnumType.STRING)
    var mode: ConsumerMode = ConsumerMode.None

    @Enumerated(value = EnumType.STRING)
    var wiring: WiringMode = WiringMode.Unknown

    @Column(nullable = false)
    lateinit var name: String

    @Column(nullable = false)
    var standard: Boolean = false

    @OneToMany(cascade = [CascadeType.MERGE], mappedBy = "group")
    var systems = mutableListOf<BatterySystemEntity>()
}
