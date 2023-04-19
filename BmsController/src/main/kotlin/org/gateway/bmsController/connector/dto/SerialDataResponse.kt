package org.gateway.bmsController.connector.dto

import kotlinx.serialization.Serializable

@Serializable
class SerialDataResponse {
    lateinit var data: String
    lateinit var command: SerialDataCommand
}
