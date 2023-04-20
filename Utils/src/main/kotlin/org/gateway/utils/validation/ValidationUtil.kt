package org.gateway.utils.validation

object ValidationUtil {

    fun verifyIfEmptyAndThrow(byteArray: ByteArray) {
        if (byteArray.isEmpty())
            throw Exception("Byte array is empty")
    }

    fun verifyIfNotEmptyAndThrow(value: String) {
        if (value.isNotEmpty())
            throw Exception("Value is NOT empty")
    }

    fun verifyIfEmptyAndThrow(value: String) {
        if (value.isEmpty())
            throw Exception("Value is empty")
    }

    fun verifyIfEmptyAndThrow(list: Iterable<Any>) {
        if (list.toList().isEmpty())
            throw Exception("List is empty")
    }

    fun verifyIfInRangeAndThrow(value: Int, min: Int, max: Int) {
        if (value !in min..max)
            throw Exception("Value '$value' not between $min and $max")
    }

    fun verifyIfValueLowerZeroAndThrow(value: Double) {
        if (value < 0.0)
            throw Exception("Value '$value' is lower zero")
    }

    fun verifyIfValueLowerZeroAndThrow(value: Int) = verifyIfValueLowerZeroAndThrow(value = value.toDouble())

    fun verifyIfValueZeroOrLowerAndThrow(value: Double) {
        if (value <= 0.0)
            throw Exception("Value '$value' is zero or lower")
    }

    fun verifyIfValueZeroOrLowerAndThrow(value: Long) = verifyIfValueZeroOrLowerAndThrow(value = value.toDouble())

    fun verifyIfValueZeroOrLowerAndThrow(value: Int) = verifyIfValueZeroOrLowerAndThrow(value = value.toDouble())
}
