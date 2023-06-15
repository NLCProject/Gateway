package org.gateway.bmsController.connector

import org.gateway.utils.serialzation.JsonSerialization
import gnu.io.CommPortIdentifier
import gnu.io.SerialPort
import gnu.io.SerialPortEvent
import gnu.io.SerialPortEventListener
import org.gateway.bmsController.connector.dto.SerialDataResponse
import org.gateway.bmsController.connector.interfaces.ISerialConnectorService
import org.gateway.bmsController.connector.interfaces.ISerialDataHandler
import org.gateway.websocketInternalApi.InternalWebsocketSessionSender
import org.gateway.websocketInternalApi.messages.SystemDetected
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.OutputStream

@Service
class SerialConnectorService @Autowired constructor(
    private val serialDataHandler: ISerialDataHandler,
    private val sessionSender: InternalWebsocketSessionSender
): SerialPortEventListener, ISerialConnectorService, JsonSerialization() {

    @Value("\${gateway.bms.port}")
    var port: String = ""

    @Value("\${gateway.bms.port.override}")
    var overridePort: Boolean = false

    @Value("\${gateway.bms.baudRate}")
    var baudRate: String = ""

    private val timeout = 2000
    private var started = false
    private val logger = LoggerFactory.getLogger(this::class.java)

    private lateinit var output: OutputStream
    private lateinit var serialPort: SerialPort
    private lateinit var inputStream: InputStream

    @Scheduled(cron = "*/10 * * * * *")
    override fun initialize() {
        try {
            if (started || port.isEmpty() || baudRate.isEmpty()) return
            logger.info("Starting serial connector")

            if (overridePort)
                System.setProperty("gnu.io.rxtx.SerialPorts", port)

            val portId = getPortId() ?: return logger.info("Could not find port '$port'")
            logger.info("Configuring port '$port'")
            serialPort = portId.open(this.javaClass.name, timeout) as SerialPort
            serialPort.setSerialPortParams(
                baudRate.toInt(),
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

            sessionSender.sendMessage(message = SystemDetected())
            logger.info("Serial connector started")
        } catch (exception: Exception) {
            closeConnection()
            logger.error("Error while starting serial connector | ${exception.message}")
        }
    }

    override fun serialEvent(event: SerialPortEvent) {
        try {
            if (event.eventType != SerialPortEvent.DATA_AVAILABLE)
                return

            if (inputStream.available() == 0)
                return

            val readBuffer = ByteArray(inputStream.available())
            while (inputStream.available() > 0)
                inputStream.read(readBuffer)

            serialDataHandler.handleData(data = String(readBuffer))
            Thread.sleep(500)
        } catch (exception: Exception) {
            closeConnection()
            logger.error("Error while reading serial data | ${exception.message}")
        }
    }

    override fun sendData(dto: SerialDataResponse) {
        try {
            logger.info("Sending serial data response command '${dto.command}'")
            val data = encode(model = dto)
            output.write(data.toByteArray())
        } catch (exception: Exception) {
            closeConnection()
            logger.error("Error while reading sending data | ${exception.message}")
        }
    }

    override fun closeConnection() {
        started = false

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
