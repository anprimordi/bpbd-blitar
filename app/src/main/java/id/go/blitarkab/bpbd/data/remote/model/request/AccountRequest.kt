package id.go.blitarkab.bpbd.data.remote.model.request

import com.google.gson.annotations.SerializedName
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource

class AccountRequest {

    companion object {
        fun createLogin(email: String, password: String): LoginRequest {
            return LoginRequest(
                email = email,
                password = password
            )
        }

        fun createRegister(data: AccountDataSource.AccountData): RegisterRequest {
            return RegisterRequest(
                fullName = data.fullName,
                email = data.email,
                gender = data.gender,
                nik = data.nik,
                phoneNumber = data.phoneNumber,
                password = data.password,
                passwordConfirmation = data.password
            )
        }

        fun createUpdate(data: AccountDataSource.AccountData): UpdateRequest {
            return UpdateRequest(
                fullName = data.fullName,
                email = data.email,
                gender = if (data.gender.isBlank()) null else data.gender,
                nik = if (data.nik.isBlank()) null else data.nik,
                phoneNumber = if (data.phoneNumber.isBlank()) null else data.phoneNumber,
                password = data.password,
            )
        }

        fun createForgotPassword(email: String): ForgotPasswordRequest {
            return ForgotPasswordRequest(
                email = email
            )
        }
    }

    data class LoginRequest(
        @SerializedName(value = "email") val email: String,
        @SerializedName(value = "password") val password: String
    )

    data class RegisterRequest(
        @SerializedName(value = "fullname") val fullName: String,
        @SerializedName(value = "email") val email: String,
        @SerializedName(value = "gender") val gender: String,
        @SerializedName(value = "nik") val nik: String,
        @SerializedName(value = "mobile_number") val phoneNumber: String,
        @SerializedName(value = "password") val password: String,
        @SerializedName(value = "password_confirmation") val passwordConfirmation: String
    )

    data class UpdateRequest(
        @SerializedName(value = "fullname") val fullName: String,
        @SerializedName(value = "email") val email: String,
        @SerializedName(value = "gender") val gender: String?,
        @SerializedName(value = "nik") val nik: String?,
        @SerializedName(value = "mobile_number") val phoneNumber: String?,
        @SerializedName(value = "password") val password: String
    )

    data class ForgotPasswordRequest(
        @SerializedName(value = "email") val email: String
    )

}