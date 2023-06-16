package org.gateway.bmsController.connector.interfaces

import org.gateway.bmsController.connector.dto.SerialDataRequest

interface ISerialDataHandler {

    fun handleData(data: String)

    fun handleData(dto: SerialDataRequest)
}
