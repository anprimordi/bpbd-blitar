package id.go.blitarkab.bpbd.data.remote.service

import id.go.blitarkab.bpbd.data.remote.model.common.Wrapper
import id.go.blitarkab.bpbd.data.remote.model.request.AccountRequest
import id.go.blitarkab.bpbd.data.remote.model.response.AccountResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

@JvmSuppressWildcards
interface AccountService {

    @POST(value = "login")
    suspend fun login(@Body body: AccountRequest.LoginRequest): Wrapper<AccountResponse>

    @POST(value = "register")
    suspend fun register(@Body body: AccountRequest.RegisterRequest): Wrapper<AccountResponse>

    @POST(value = "forgot_password")
    suspend fun forgotPassword(@Body body: AccountRequest.ForgotPasswordRequest): Wrapper<Unit>

    @GET(value = "user/profile")
    suspend fun getUserDetail(): Wrapper<AccountResponse>

    @GET(value = "officer/profile")
    suspend fun getOfficerDetail(): Wrapper<AccountResponse>

    @POST(value = "user/profile/update")
    suspend fun updateUserProfile(@Body body: AccountRequest.UpdateRequest): Wrapper<AccountResponse>

    @Headers(value = ["Enctype: multipart/form-data"])
    @Multipart
    @POST(value = "user/profile/update-photo")
    suspend fun updateUserPhoto(
        @Part photoProfile: MultipartBody.Part,
    ): Wrapper<AccountResponse>

    @POST(value = "officer/profile/update")
    suspend fun updateOperatorProfile(@Body body: AccountRequest.UpdateRequest): Wrapper<AccountResponse>

    @Headers(value = ["Enctype: multipart/form-data"])
    @Multipart
    @POST(value = "officer/profile/update-photo")
    suspend fun updateOperatorPhoto(
        @Part photoProfile: MultipartBody.Part,
    ): Wrapper<AccountResponse>

}