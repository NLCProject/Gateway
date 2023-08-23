package org.gateway.storageApi.dto

abstract class Dto {
    lateinit var id: String
    var timestampCreated: Long = 0
    var timestampLastModified: Long? = null
}
