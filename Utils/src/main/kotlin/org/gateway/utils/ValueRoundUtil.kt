package org.gateway.utils

import java.math.BigDecimal
import java.math.RoundingMode

object ValueRoundUtil {

    fun roundDouble(value: Double): Double = BigDecimal(value)
        .setScale(2, RoundingMode.HALF_EVEN)
        .toString()
        .toDouble()
}
