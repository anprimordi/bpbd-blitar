package id.go.blitarkab.bpbd.domain.model

import id.go.blitarkab.bpbd.domain.model.common.Constants

data class EarthQuake(
    val datetime: Long,
    val latitude: Double,
    val longitude: Double,
    val magnitude: String,
    val depth: String,
    val area: String,
    val areaDescription: String,
    val shakeMapImageUrl: String
) {

    companion object {
        val DEFAULT: EarthQuake = EarthQuake(
            datetime = Constants.DEFAULT_DATE_TIME,
            latitude = 0.0,
            longitude = 0.0,
            magnitude = "",
            depth = "",
            area = "",
            areaDescription = "",
            shakeMapImageUrl = ""
        )
    }

}