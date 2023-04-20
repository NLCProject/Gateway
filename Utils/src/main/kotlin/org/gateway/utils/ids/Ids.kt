package org.gateway.utils.ids

import java.util.*

object Ids {

    fun getRandomId(): String = UUID.randomUUID().toString()
}
