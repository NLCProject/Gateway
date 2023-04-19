package org.gateway.connector

import gnu.io.CommPortIdentifier
import gnu.io.SerialPort
import gnu.io.SerialPortEvent
import gnu.io.SerialPortEventListener
import org.gateway.connector.dto.SerialDataResponse
import org.gateway.connector.interfaces.ISerialConnectorService
import org.gateway.connector.interfaces.ISerialDataHandler
import org.gateway.services.serialization.JsonSerialization
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.OutputStream

@Service
class SerialConnectorService @Autowired constructor(
    private val serialDataHandler: ISerialDataHandler
): SerialPortEventListener, ISerialConnectorService, JsonSerialization() {

    @Value("\${gateway.bms.port}")
    var port: String = ""

    private val timeout = 2000
    private var started = false
    private val dataRate = 115200
    private val logger = LoggerFactory.getLogger(this::class.java)

    private lateinit var output: OutputStream
    private lateinit var serialPort: SerialPort
    private lateinit var inputStream: InputStream

    override fun initialize() {
        try {
            if (started || port.isEmpty()) return
            logger.info("Starting serial connector")

            val portId = getPortId() ?: return logger.debug("Could not find port '$port'")
            logger.info("Configuring port '$port'")
            serialPort = portId.open(this.javaClass.name, timeout) as SerialPort
            serialPort.setSerialPortParams(
                dataRate,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE
            )

            serialPort.isDTR = false
            serialPort.isRTS = false

            logger.info("Loading I/O streams")
            inputStream = serialPort.inputStream
            output = serialPort.outputStream

            logger.info("Loading serial event listeners")
            serialPort.addEventListener(this)
            serialPort.notifyOnDataAvailable(true)
            started = true
            logger.info("Serial connector started")
        } catch (exception: Exception) {
            logger.error("Error while starting serial connector | ${exception.message}")
        }
    }

    override fun serialEvent(event: SerialPortEvent) {
        try {
            if (event.eventType == SerialPortEvent.DATA_AVAILABLE) {
                val readBuffer = ByteArray(inputStream.available())
                while (inputStream.available() > 0)
                    inputStream.read(readBuffer)

                serialDataHandler.handleData(data = String(readBuffer))
            }
        } catch (exception: Exception) {
            logger.error("Error while reading serial data | ${exception.message}")
        }
    }

    override fun sendData(dto: SerialDataResponse) {
        try {
            logger.info("Sending serial data response command '${dto.command}'")
            val data = encode(model = dto)
            output.write(data.toByteArray())
        } catch (exception: Exception) {
            logger.error("Error while reading sending data | ${exception.message}")
        }
    }

    override fun closeConnection() {
        try {
            logger.info("Closing serial connection")
            serialPort.removeEventListener()
            serialPort.close()
        } catch (exception: Exception) {
            logger.error("Error while closing serial connection | ${exception.message}")
        }
    }

    private fun getPortId(): CommPortIdentifier? = CommPortIdentifier
        .getPortIdentifiers()
        .toList()
        .filterIsInstance<CommPortIdentifier>()
        .firstOrNull { identifier -> identifier.name == port }
}
