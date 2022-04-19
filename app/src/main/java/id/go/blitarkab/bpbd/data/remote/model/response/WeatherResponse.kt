package id.go.blitarkab.bpbd.data.remote.model.response

import com.google.gson.annotations.SerializedName
import id.go.blitarkab.bpbd.domain.model.Weather

data class WeatherResponse(
    @SerializedName(value = "city") val area: String? = null,
    @SerializedName(value = "humidity") val humidity: Humidity? = null,
    @SerializedName(value = "temperature") val temperature: Temperature? = null,
    @SerializedName(value = "wind_speed") val windSpeed: WindSpeed? = null,
    @SerializedName(value = "wind_direction") val windDirection: WindDirection? = null,
    @SerializedName(value = "weather") val weather: WeatherValue? = null,
) {

    data class Humidity(
        @SerializedName(value = "today_min") val todayMin: String? = null,
        @SerializedName(value = "today_max") val todayMax: String? = null,
        @SerializedName(value = "tomorrow_min") val tomorrowMin: String? = null,
        @SerializedName(value = "tomorrow_max") val tomorrowMax: String? = null,
    )

    data class Temperature(
        @SerializedName(value = "today_min") val todayMin: String? = null,
        @SerializedName(value = "today_max") val todayMax: String? = null,
        @SerializedName(value = "tomorrow_min") val tomorrowMin: String? = null,
        @SerializedName(value = "tomorrow_max") val tomorrowMax: String? = null,
    )

    data class WindSpeed(
        @SerializedName(value = "today_avg") val todayAvg: String? = null,
        @SerializedName(value = "tomorrow_avg") val tomorrowAvg: String? = null
    )

    data class WindDirection(
        @SerializedName(value = "today_avg") val todayAvg: String? = null,
        @SerializedName(value = "tomorrow_avg") val tomorrowAvg: String? = null
    )

    data class WeatherValue(
        @SerializedName(value = "today") val today: String? = null,
        @SerializedName(value = "tomorrow") val tomorrow: String? = null
    )

    fun toDomain(): Weather {
        val minTemp = temperature?.todayMin?.toDoubleOrNull() ?: 0.0
        val maxTemp = temperature?.todayMax?.toDoubleOrNull() ?: 0.0
        val temp = ((minTemp + maxTemp) / 2).toInt()

        val minHum = humidity?.todayMin?.toDoubleOrNull() ?: 0.0
        val maxHum = humidity?.todayMax?.toDoubleOrNull() ?: 0.0
        val hum = ((minHum + maxHum) / 2).toInt()

        val windSpeed = this.windSpeed?.todayAvg ?: ""
        val windDirection = this.windDirection?.todayAvg ?: ""
        val weather = this.weather?.today ?: ""
        return Weather(
            area = area ?: Weather.DEFAULT.area,
            weather = weather,
            temperature = temp,
            humidity = hum,
            wind = windSpeed
        )
    }

}