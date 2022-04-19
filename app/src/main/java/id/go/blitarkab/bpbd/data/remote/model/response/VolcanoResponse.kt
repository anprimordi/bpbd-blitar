package id.go.blitarkab.bpbd.data.remote.model.response

import com.google.gson.annotations.SerializedName
import id.go.blitarkab.bpbd.domain.model.Volcano

data class VolcanoResponse(
    @SerializedName(value = "mountain") val mountain: String? = null,
    @SerializedName(value = "status") val status: String? = null,
    @SerializedName(value = "date") val date: String? = null,
) {

    fun toDomain(): Volcano {
        return Volcano(
            mountain = mountain ?: Volcano.DEFAULT.mountain,
            status = status ?: Volcano.DEFAULT.status,
            date = date ?: Volcano.DEFAULT.date
        )
    }

}