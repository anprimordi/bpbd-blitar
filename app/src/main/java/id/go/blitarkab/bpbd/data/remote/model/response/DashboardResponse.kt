package id.go.blitarkab.bpbd.data.remote.model.response

import com.google.gson.annotations.SerializedName
import id.go.blitarkab.bpbd.domain.model.Dashboard

data class DashboardResponse(
    @SerializedName(value = "earthquake") val earthQuake: EarthQuakeResponse? = null,
    @SerializedName(value = "weather") val weather: WeatherResponse? = null,
    @SerializedName(value = "covid") val covid: CovidResponse.Province? = null
) {

    fun toDomain(): Dashboard {
        return Dashboard(
            covid = covid?.toDomain() ?: Dashboard.DEFAULT.covid,
            earthQuake = earthQuake?.toDomain() ?: Dashboard.DEFAULT.earthQuake,
        )
    }

}