package id.go.blitarkab.bpbd.data

import id.go.blitarkab.bpbd.di.datasource.LocalDataSource
import id.go.blitarkab.bpbd.di.datasource.RemoteDataSource
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Result
import javax.inject.Inject

class AccountRepository @Inject constructor(
    @LocalDataSource private val localSource: AccountDataSource,
    @RemoteDataSource private val remoteSource: AccountDataSource
) : AccountDataSource {

    override fun getAccount(): Result<Account> {
        return localSource.getAccount()
    }

    override fun saveAccount(account: Account): Result<Unit> {
        return localSource.saveAccount(account)
    }

    override suspend fun login(email: String, password: String): Result<Account> {
        return remoteSource.login(email, password)
    }

    override suspend fun register(data: AccountDataSource.AccountData): Result<Account> {
        return remoteSource.register(data)
    }

    override suspend fun forgotPassword(email: String): Result<Unit> {
        return remoteSource.forgotPassword(email)
    }

    override suspend fun logout(): Result<Unit> {
        return localSource.logout()
    }

    override suspend fun getUserDetail(): Result<Account> {
        return remoteSource.getUserDetail()
    }

    override suspend fun getOfficerDetail(): Result<Account> {
        return remoteSource.getOfficerDetail()
    }

    override suspend fun updateUserProfile(data: AccountDataSource.AccountData): Result<Account> {
        return remoteSource.updateUserProfile(data)
    }

    override suspend fun updateUserPhoto(
        fileName: String,
        fileContent: ByteArray
    ): Result<Account> {
        return remoteSource.updateUserPhoto(fileName, fileContent)
    }

    override suspend fun updateOperatorProfile(data: AccountDataSource.AccountData): Result<Account> {
        return remoteSource.updateOperatorProfile(data)
    }

    override suspend fun updateOperatorPhoto(
        fileName: String,
        fileContent: ByteArray
    ): Result<Account> {
        return remoteSource.updateOperatorPhoto(fileName, fileContent)
    }

}