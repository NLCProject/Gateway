package org.gateway.connector.dto

import kotlinx.serialization.Serializable

@Serializable
class SerialDataRequest {
    lateinit var data: String
    lateinit var manufacturer: String
    lateinit var serialNumber: String
    lateinit var command: SerialDataCommand
}
