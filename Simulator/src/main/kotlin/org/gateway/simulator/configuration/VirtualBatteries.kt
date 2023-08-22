package org.gateway.simulator.configuration

object VirtualBatteries {

    val clients = listOf(
        VirtualBattery(
            voltage = 5.3,
            lowerDispersion = 0.95,
            upperDispersion = 1.05,
            manufacturer = "Joy-It"
        ),

        VirtualBattery(
            voltage = 9.1,
            manufacturer = "Varta",
            lowerDispersion = 0.95,
            upperDispersion = 1.05
        ),

        VirtualBattery(
            voltage = 20.8,
            lowerDispersion = 0.95,
            upperDispersion = 1.05,
            manufacturer = "Duracell"
        ),

        VirtualBattery(
            voltage = 10.0,
            lowerDispersion = 1.0,
            upperDispersion = 1.0,
            manufacturer = "LG Energy Solution"
        ),

        VirtualBattery(
            voltage = 4.7,
            lowerDispersion = 0.95,
            upperDispersion = 1.05,
            manufacturer = "Panasonic"
        ),

        VirtualBattery(
            voltage = 15.6,
            lowerDispersion = 0.95,
            upperDispersion = 1.05,
            manufacturer = "Samsung SDI"
        )
    )
}
