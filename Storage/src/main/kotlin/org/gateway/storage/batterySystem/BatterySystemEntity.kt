package org.gateway.storage.batterySystem

import org.gateway.storage.consumerGroup.ConsumerGroupEntity
import org.gateway.storage.framework.DistributedEntity
import org.gateway.storage.measurement.voltage.VoltageMeasurementEntity
import org.gateway.utils.battery.enums.SystemStatus
import javax.persistence.*

@Entity
@Table(name = "battery_system")
class BatterySystemEntity : DistributedEntity() {

    @Enumerated(value = EnumType.STRING)
    lateinit var status: SystemStatus

    @Column(nullable = false)
    lateinit var manufacturer: String

    @Column(nullable = false)
    lateinit var serialNumber: String

    @ManyToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "consumer_group_id")
    lateinit var group: ConsumerGroupEntity

    @OneToMany(cascade = [CascadeType.MERGE], mappedBy = "system")
    var measurements = mutableListOf<VoltageMeasurementEntity>()
}
