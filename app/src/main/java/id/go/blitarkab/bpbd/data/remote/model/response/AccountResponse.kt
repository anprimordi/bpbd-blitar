package id.go.blitarkab.bpbd.data.remote.model.response

import com.google.gson.annotations.SerializedName
import id.go.blitarkab.bpbd.domain.model.Account

data class AccountResponse(
    @SerializedName(value = "id") val id: Long? = null,
    @SerializedName(value = "token") val token: String? = null,
    @SerializedName(value = "fullname") val fullName: String? = null,
    @SerializedName(value = "email") val email: String? = null,
    @SerializedName(value = "gender") val gender: String? = null,
    @SerializedName(value = "nik") val nik: String? = null,
    @SerializedName(value = "mobile_number") val phoneNumber: String? = null,
    @SerializedName(value = "photo") val photoProfileUrl: String? = null,
    @SerializedName(value = "type") val type: String? = null,
) {

    fun toDomain(): Account {
        return Account(
            id = id ?: Account.DEFAULT.id,
            token = token ?: Account.DEFAULT.token,
            fullName = fullName ?: Account.DEFAULT.fullName,
            email = email ?: Account.DEFAULT.email,
            gender = gender ?: Account.DEFAULT.gender,
            nik = nik ?: Account.DEFAULT.nik,
            phoneNumber = phoneNumber ?: Account.DEFAULT.phoneNumber,
            photoProfileUrl = photoProfileUrl,
            type = if (type == "user") Account.Type.USER else Account.Type.OPERATOR
        )
    }

}