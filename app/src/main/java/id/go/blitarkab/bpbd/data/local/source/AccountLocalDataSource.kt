package id.go.blitarkab.bpbd.data.local.source

import com.google.gson.Gson
import id.go.blitarkab.bpbd.data.local.preference.AppPreference
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountLocalDataSource @Inject constructor(
    private val preference: AppPreference
) : AccountDataSource {

    override fun getAccount(): Result<Account> {
        return try {
            val accountJson = preference.get().getString(AppPreference.KEY_ACCOUNT, null)
            if (accountJson == null) AuthError()
            else {
                val gson = Gson()
                val account = gson.fromJson(accountJson, Account::class.java)
                if (account == null) AuthError()
                else Success(account)
            }
        } catch (ex: Exception) {
            Error.construct(ex)
        }
    }

    override fun saveAccount(account: Account): Result<Unit> {
        return try {
            val gson = Gson()
            val accountJson = gson.toJson(account) ?: throw Exception()
            preference.editor().putString(AppPreference.KEY_ACCOUNT, accountJson).apply()
            Success(Unit)
        } catch (ex: Exception) {
            Error.construct(ex)
        }
    }

    override suspend fun login(email: String, password: String): Result<Account> {
        return UnsupportedError(source = this)
    }

    override suspend fun register(data: AccountDataSource.AccountData): Result<Account> {
        return UnsupportedError(source = this)
    }

    override suspend fun forgotPassword(email: String): Result<Unit> {
        return UnsupportedError(source = this)
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            preference.editor().remove(AppPreference.KEY_ACCOUNT).apply()
            Success(Unit)
        } catch (ex: Exception) {
            Error.construct(ex)
        }
    }

    override suspend fun getUserDetail(): Result<Account> {
        return UnsupportedError(source = this)
    }

    override suspend fun getOfficerDetail(): Result<Account> {
        return UnsupportedError(source = this)
    }

    override suspend fun updateUserProfile(data: AccountDataSource.AccountData): Result<Account> {
        return UnsupportedError(source = this)
    }

    override suspend fun updateUserPhoto(
        fileName: String,
        fileContent: ByteArray
    ): Result<Account> {
        return UnsupportedError(source = this)
    }

    override suspend fun updateOperatorProfile(data: AccountDataSource.AccountData): Result<Account> {
        return UnsupportedError(source = this)
    }

    override suspend fun updateOperatorPhoto(
        fileName: String,
        fileContent: ByteArray
    ): Result<Account> {
        return UnsupportedError(source = this)
    }

}