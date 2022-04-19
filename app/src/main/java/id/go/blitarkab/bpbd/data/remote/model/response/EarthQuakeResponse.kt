package id.go.blitarkab.bpbd.data.remote.model.response

import com.google.gson.annotations.SerializedName
import id.go.blitarkab.bpbd.data.remote.util.RemoteDateTimeUtils
import id.go.blitarkab.bpbd.domain.model.EarthQuake

data class EarthQuakeResponse(
    @SerializedName(value = "DateTime") val datetime: String? = null,
    @SerializedName(value = "Coordinates") val coordinate: String? = null,
    @SerializedName(value = "Magnitude") val magnitude: String? = null,
    @SerializedName(value = "Kedalaman") val depth: String? = null,
    @SerializedName(value = "Dirasakan") val area: String? = null,
    @SerializedName(value = "Wilayah") val areaDescription: String? = null,
    @SerializedName(value = "Shakemap") val shakeMapImageUrl: String? = null
) {

    fun toDomain(): EarthQuake {
        val coordinates = coordinate?.split(",") ?: emptyList()
        return EarthQuake(
            datetime = if (datetime != null) RemoteDateTimeUtils.parseEarthQuakeDatetime(datetime).time else EarthQuake.DEFAULT.datetime,
            latitude = coordinates.getOrNull(index = 0)?.toDoubleOrNull() ?: EarthQuake.DEFAULT.latitude,
            longitude = coordinates.getOrNull(index = 1)?.toDoubleOrNull() ?: EarthQuake.DEFAULT.latitude,
            magnitude = magnitude ?: EarthQuake.DEFAULT.magnitude,
            depth = depth ?: EarthQuake.DEFAULT.depth,
            area = area ?: EarthQuake.DEFAULT.area,
            areaDescription = areaDescription ?: EarthQuake.DEFAULT.areaDescription,
            shakeMapImageUrl = shakeMapImageUrl ?: EarthQuake.DEFAULT.shakeMapImageUrl
        )
    }

}