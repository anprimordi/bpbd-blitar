package id.go.blitarkab.bpbd.domain.datasource

import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Result

interface AccountDataSource {

    fun getAccount(): Result<Account>
    fun saveAccount(account: Account): Result<Unit>

    suspend fun login(email: String, password: String): Result<Account>
    suspend fun register(data: AccountData): Result<Account>
    suspend fun forgotPassword(email: String): Result<Unit>
    suspend fun logout(): Result<Unit>

    suspend fun getUserDetail(): Result<Account>
    suspend fun getOfficerDetail(): Result<Account>

    suspend fun updateUserProfile(data: AccountData): Result<Account>
    suspend fun updateUserPhoto(fileName: String, fileContent: ByteArray): Result<Account>

    suspend fun updateOperatorProfile(data: AccountData): Result<Account>
    suspend fun updateOperatorPhoto(fileName: String, fileContent: ByteArray): Result<Account>

    data class AccountData(
        val fullName: String,
        val email: String,
        val gender: String,
        val nik: String,
        val phoneNumber: String,
        val password: String,
    )

}