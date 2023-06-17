package org.gateway.utils

import org.gateway.utils.ids.Ids

object SystemConfiguration {
    var serialNumber: String = Ids.getRandomId()
}
