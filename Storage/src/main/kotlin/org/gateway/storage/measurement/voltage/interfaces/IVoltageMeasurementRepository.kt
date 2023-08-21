package org.gateway.storage.measurement.voltage.interfaces

import org.gateway.storage.framework.ICrudlRepository
import org.gateway.storage.measurement.voltage.VoltageMeasurementEntity
import org.springframework.stereotype.Service
import java.util.Optional

@Service
interface IVoltageMeasurementRepository : ICrudlRepository<VoltageMeasurementEntity> {

    fun getFirstBySerialNumberOrderByTimestampCreatedDesc(serialNumber: String): Optional<VoltageMeasurementEntity>
}
