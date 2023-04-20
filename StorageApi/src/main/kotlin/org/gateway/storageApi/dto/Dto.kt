package org.gateway.storageApi.dto

import java.time.ZonedDateTime

abstract class Dto {
    lateinit var id: String
    var timestampCreated: Long = 0
    var timestampLastModified: Long? = null
    lateinit var dateTimeCreated: ZonedDateTime
    var dateTimeLastModified: ZonedDateTime? = null
}
