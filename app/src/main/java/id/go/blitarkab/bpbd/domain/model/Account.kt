package id.go.blitarkab.bpbd.domain.model

data class Account(
    val id: Long,
    val token: String,
    val fullName: String,
    val email: String,
    val gender: String,
    val nik: String,
    val phoneNumber: String,
    val photoProfileUrl: String?,
    val type: Type
) {

    companion object {
        val DEFAULT: Account = Account(
            id = 0,
            token = "",
            fullName = "",
            email = "",
            gender = "",
            nik = "",
            phoneNumber = "",
            photoProfileUrl = null,
            type = Type.USER
        )
    }

    enum class Type {
        USER, OPERATOR
    }

}