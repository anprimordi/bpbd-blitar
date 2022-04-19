package id.go.blitarkab.bpbd.data.remote.source

import id.go.blitarkab.bpbd.data.remote.AppRemoteClient
import id.go.blitarkab.bpbd.data.remote.model.request.AccountRequest
import id.go.blitarkab.bpbd.data.remote.service.AccountService
import id.go.blitarkab.bpbd.data.remote.util.execute
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.AuthError
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.domain.model.common.UnsupportedError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AccountRemoteDataSource @Inject constructor(
    private val remoteClient: AppRemoteClient
) : AccountDataSource {

    override fun getAccount(): Result<Account> {
        return UnsupportedError(source = this)
    }

    override fun saveAccount(account: Account): Result<Unit> {
        return UnsupportedError(source = this)
    }

    override suspend fun login(email: String, password: String): Result<Account> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(AccountService::class.java)
                val request = AccountRequest.createLogin(email, password)
                service.login(request)
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else AuthError()
            }
        }
    }

    override suspend fun register(data: AccountDataSource.AccountData): Result<Account> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(AccountService::class.java)
                val request = AccountRequest.createRegister(data)
                service.register(request)
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else AuthError()
            }
        }
    }

    override suspend fun forgotPassword(email: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(AccountService::class.java)
                val request = AccountRequest.createForgotPassword(email)
                service.forgotPassword(request)
            }.mapTo { response ->
                if (response.isSuccess) Success(Unit)
                else AuthError()
            }
        }
    }

    override suspend fun logout(): Result<Unit> {
        return UnsupportedError(source = this)
    }

    override suspend fun getUserDetail(): Result<Account> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(AccountService::class.java)
                service.getUserDetail()
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else AuthError()
            }
        }
    }

    override suspend fun getOfficerDetail(): Result<Account> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(AccountService::class.java)
                service.getOfficerDetail()
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else AuthError()
            }
        }
    }

    override suspend fun updateUserProfile(data: AccountDataSource.AccountData): Result<Account> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(AccountService::class.java)
                val request = AccountRequest.createUpdate(data)
                service.updateUserProfile(request)
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else AuthError()
            }
        }
    }

    override suspend fun updateUserPhoto(
        fileName: String,
        fileContent: ByteArray
    ): Result<Account> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(AccountService::class.java)

                val requestPhoto = fileContent.toRequestBody(contentType = "image/*".toMediaType())
                val bodyPhoto = MultipartBody.Part.createFormData(
                    name = "photo",
                    filename = fileName,
                    body = requestPhoto
                )

                service.updateUserPhoto(bodyPhoto)
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else AuthError()
            }
        }
    }

    override suspend fun updateOperatorProfile(data: AccountDataSource.AccountData): Result<Account> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(AccountService::class.java)
                val request = AccountRequest.createUpdate(data)
                service.updateOperatorProfile(request)
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else AuthError()
            }
        }
    }

    override suspend fun updateOperatorPhoto(
        fileName: String,
        fileContent: ByteArray
    ): Result<Account> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(AccountService::class.java)

                val requestPhoto = fileContent.toRequestBody(contentType = "image/*".toMediaType())
                val bodyPhoto = MultipartBody.Part.createFormData(
                    name = "photo",
                    filename = fileName,
                    body = requestPhoto
                )

                service.updateOperatorPhoto(bodyPhoto)
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else AuthError()
            }
        }
    }

}