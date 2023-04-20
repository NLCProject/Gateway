package org.gateway.storage.batterySystem

import org.gateway.storage.framework.DistributedEntity
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
}
