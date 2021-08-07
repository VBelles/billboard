package io.github.vbelles.billboard.data

import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.io.IOException

/**
 * Builds a [Result] from retrofit request, handling connectivity failure and parsing exceptions
 */
inline fun <reified V> resultOf(block: () -> Response<V>): Result<V> {
    return try {
        val response = block()
        val body = response.body()
        if (response.isSuccessful) {
            when {
                null is V -> Result.success(body as V)
                V::class == Unit::class -> Result.success(Unit as V)
                body != null -> Result.success(body)
                else -> Result.failure(UnknownError())
            }
        } else {
            // Here we should map domain specific errors
            Result.failure(UnknownError())
        }
    } catch (ioException: IOException) {
        Result.failure(ioException)
    } catch (serializationException: SerializationException) {
        Result.failure(serializationException)
    }
}