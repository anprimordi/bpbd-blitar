package id.go.blitarkab.bpbd.domain.model.common

sealed class Result<T>(
    open val message: String,
    open val data: T?
) {

    fun <S> map(mapper: (t: T) -> S): Result<S> {
        return try {
            when (this) {
                is Success -> {
                    Success(data = mapper(data), message = message)
                }
                is GeneralError -> {
                    val currentData = data
                    GeneralError(message, if (currentData != null) mapper(currentData) else null)
                }
                is InvalidDataError -> InvalidDataError(message)
                is AuthError -> AuthError(message)
                is NotFoundError -> NotFoundError(message)
                is NetworkError -> NetworkError()
                is UnsupportedError -> UnsupportedError(message)
                else -> Loading()
            }
        } catch (ex: IllegalStateException) {
            Error.invalidData(ex)
        } catch (ex: Exception) {
            Error.construct(ex)
        }
    }

    fun <S> mapTo(mapper: (t: T) -> Result<S>): Result<S> {
        return try {
            when (this) {
                is Success -> mapper(data)
                is GeneralError -> GeneralError(message)
                is InvalidDataError -> GeneralError(message)
                is AuthError -> AuthError(message)
                is NotFoundError -> NotFoundError(message)
                is NetworkError -> NetworkError()
                is UnsupportedError -> UnsupportedError(message)
                else -> Loading()
            }
        } catch (ex: IllegalStateException) {
            Error.invalidData(ex)
        } catch (ex: Exception) {
            Error.construct(ex)
        }
    }

}

class Loading<T>(message: String = "Please Wait", data: T? = null) : Result<T>(message, data)
class Success<T>(override val data: T, message: String = "Success") : Result<T>(message, data)

sealed class Error<T>(message: String, data: T? = null) : Result<T>(message, data) {
    companion object {
        private const val UNKNOWN_ERROR_MESSAGE: String = "Unknown error occurred."
        private const val SERVER_ERROR_MESSAGE: String = "Internal server error."
        private const val INVALID_DATA_ERROR_MESSAGE: String = "Error while try to parsing data."

        fun <T> unknown(): GeneralError<T> {
            return GeneralError(UNKNOWN_ERROR_MESSAGE)
        }

        fun <T> general(message: String? = null): Error<T> {
            val msg = if (message != null) "$UNKNOWN_ERROR_MESSAGE\n($message)." else UNKNOWN_ERROR_MESSAGE
            return GeneralError(msg)
        }

        fun <T> server(): GeneralError<T> {
            return GeneralError(SERVER_ERROR_MESSAGE)
        }

        fun <T> invalidData(ex: IllegalStateException): InvalidDataError<T> {
            val exception = if (ex.message != null) " (${ex.message})" else ""
            val message = INVALID_DATA_ERROR_MESSAGE + exception
            return InvalidDataError(message)
        }

        fun <T> construct(throwable: Throwable?): GeneralError<T> {
            return GeneralError(message = throwable?.message ?: UNKNOWN_ERROR_MESSAGE)
        }
    }
}

class GeneralError<T>(message: String, data: T? = null) : Error<T>(message, data)
class InvalidDataError<T>(message: String, data: T? = null) : Error<T>(message, data)
class AuthError<T>(message: String = "Unauthorized", data: T? = null) : Error<T>(message, data)
class NotFoundError<T>(message: String = "Data not found", data: T? = null) :
    Error<T>(message, data)

class NetworkError<T> : Error<T>(message = "No internet connection.")
class UnsupportedError<T>(source: Any) :
    Error<T>("${source::class.simpleName} doesn't support this feature.")