package org.gateway.storage.measurement.voltage

import org.gateway.storage.framework.DistributedEntity
import javax.persistence.*

@Entity
@Table(name = "voltage_measurement")
class VoltageMeasurementEntity : DistributedEntity() {

    @Column(nullable = false)
    var measuredValue: Double = 0.0

    @Column(nullable = false)
    lateinit var manufacturer: String

    @Column(nullable = false)
    lateinit var serialNumber: String
}
