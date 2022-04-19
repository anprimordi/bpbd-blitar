package id.go.blitarkab.bpbd.domain.model

data class Weather(
    val area: String,
    val weather: String,
    val temperature: Int,
    val humidity: Int,
    val wind: String
) {

    companion object {
        val DEFAULT: Weather = Weather(
            area = "",
            weather = "",
            temperature = 0,
            humidity = 0,
            wind = ""
        )
    }

    override fun toString(): String {
        return area
    }

}