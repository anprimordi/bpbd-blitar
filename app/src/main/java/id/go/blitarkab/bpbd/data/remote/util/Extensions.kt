package id.go.blitarkab.bpbd.data.remote.util

import id.go.blitarkab.bpbd.domain.model.common.*
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

suspend fun <T> execute(call: suspend () -> T): Result<T> {
    return try {
        val response = call()
        Success(data = response)
    } catch (ex: IOException) {
        Timber.e(ex)
        NetworkError()
    } catch (ex: HttpException) {
        Timber.e(ex)
        when (ex.code()) {
            401 -> AuthError()
            404 -> NotFoundError()
            else -> Error.general(
                if (ex.message().isNotBlank()) ex.message() else "Error Code ${ex.code()}"
            )
        }
    } catch (ex: Exception) {
        Timber.e(ex)
        Error.construct(ex)
    }
}