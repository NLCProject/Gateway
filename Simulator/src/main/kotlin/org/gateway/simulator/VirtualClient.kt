package org.gateway.simulator

import org.gateway.utils.ids.Ids

class VirtualClient(
    var baseValue: Double,
    var manufacturer: String,
    var serialNumber: String = Ids.getRandomId()
)
