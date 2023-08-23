package org.gateway.simulator.configuration

object VirtualBatteries {

    val clients = listOf(
        VirtualBattery(
            voltage = 5.3,
            lowerDispersion = 0.99,
            upperDispersion = 1.01,
            manufacturer = "Joy-It"
        ),

        VirtualBattery(
            voltage = 9.1,
            manufacturer = "Varta",
            lowerDispersion = 0.98,
            upperDispersion = 1.03
        ),

        VirtualBattery(
            voltage = 20.8,
            lowerDispersion = 0.995,
            upperDispersion = 1.005,
            manufacturer = "Duracell"
        ),

        VirtualBattery(
            voltage = 10.0,
            lowerDispersion = 0.992,
            upperDispersion = 1.01,
            manufacturer = "LG Energy Solution"
        ),

        VirtualBattery(
            voltage = 4.7,
            lowerDispersion = 0.995,
            upperDispersion = 1.005,
            manufacturer = "Panasonic"
        ),

        VirtualBattery(
            voltage = 15.6,
            lowerDispersion = 0.99,
            upperDispersion = 1.02,
            manufacturer = "Samsung SDI"
        )
    )
}
