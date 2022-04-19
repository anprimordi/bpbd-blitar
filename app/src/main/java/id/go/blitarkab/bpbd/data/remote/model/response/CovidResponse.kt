package id.go.blitarkab.bpbd.data.remote.model.response

import com.google.gson.annotations.SerializedName
import id.go.blitarkab.bpbd.domain.model.Covid

data class CovidResponse(
    @SerializedName(value = "country") val country: Country? = null,
    @SerializedName(value = "province") val province: List<ProvinceWrapper>? = null,
) {

    data class Country(
        @SerializedName(value = "name") val area: String? = null,
        @SerializedName(value = "dirawat") val totalTreatment: String? = null,
        @SerializedName(value = "positif") val totalPositive: String? = null,
        @SerializedName(value = "sembuh") val totalRecovered: String? = null,
        @SerializedName(value = "meninggal") val totalDied: String? = null,
    ) {

        fun toDomain(): Covid {
            return Covid(
                area = area ?: Covid.DEFAULT.area,
                totalCase = totalPositive ?: Covid.DEFAULT.totalCase,
                totalPositive = totalTreatment ?: Covid.DEFAULT.totalPositive,
                totalRecovered = totalRecovered ?: Covid.DEFAULT.totalRecovered,
                totalDied = totalDied ?: Covid.DEFAULT.totalDied
            )
        }

    }

    data class ProvinceWrapper(
        @SerializedName(value = "attributes") val province: Province? = null
    )

    data class Province(
        @SerializedName(value = "Provinsi") val area: String? = null,
        @SerializedName(value = "Kasus_Posi") val totalPositive: Long? = null,
        @SerializedName(value = "Kasus_Semb") val totalRecovered: Long? = null,
        @SerializedName(value = "Kasus_Meni") val totalDied: Long? = null,
    ) {

        fun toDomain(): Covid {
            return Covid(
                area = area ?: Covid.DEFAULT.area,
                totalCase = totalPositive?.toString() ?: Covid.DEFAULT.totalCase,
                totalPositive = totalPositive?.toString() ?: Covid.DEFAULT.totalPositive,
                totalRecovered = totalRecovered?.toString() ?: Covid.DEFAULT.totalRecovered,
                totalDied = totalDied?.toString() ?: Covid.DEFAULT.totalDied
            )
        }

    }

}