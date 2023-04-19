package org.gateway.bmsController

import kotlinx.serialization.UnsafeSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 *  ATTENTION: At the end of the json it is possible that unexpected tokens are appended. That's causing an
 *  exception ("Reader cannot consume") and appears when the string is received via a socket connection.
 *  So the string is sliced to get rid of them.
 */
abstract class JsonSerialization {

    protected val jsonProperty = Json { ignoreUnknownKeys = true }

    @OptIn(UnsafeSerializationApi::class)
    protected inline fun <reified T : Any> decode(data: String): T =
        Json.decodeFromString(
            T::class.serializer(),
            parseData(data = data).slice(IntRange(start = 0, endInclusive = data.lastIndexOf("}")))
        )

    @OptIn(UnsafeSerializationApi::class)
    protected inline fun <reified T : Any> decodeAndIgnoreUnknownKeys(data: String): T =
        jsonProperty.decodeFromString(
            T::class.serializer(),
            parseData(data = data).slice(IntRange(start = 0, endInclusive = data.lastIndexOf("}")))
        )

    @OptIn(UnsafeSerializationApi::class)
    protected inline fun <reified T : Any> decodeList(data: String): List<T> {
        if (data.isEmpty())
            return emptyList()

        return Json.decodeFromString(
            ListSerializer(T::class.serializer()),
            parseData(data = data).slice(IntRange(start = 0, endInclusive = data.lastIndexOf("]")))
        )
    }

    @OptIn(UnsafeSerializationApi::class)
    protected inline fun <reified T : Any> encode(model: T): String =
        parseData(data = Json.encodeToString(T::class.serializer(), model))

    @OptIn(UnsafeSerializationApi::class)
    protected inline fun <reified T : Any> encodeList(models: List<T>): String =
        parseData(data = Json.encodeToString(ListSerializer(T::class.serializer()), models))

    protected fun parseData(data: String): String = data
        .replace("\\", "")
        .replace("\"{", "{")
        .replace("}\"", "}")
        .replace("\"[", "[")
        .replace("]\"", "]")
}
