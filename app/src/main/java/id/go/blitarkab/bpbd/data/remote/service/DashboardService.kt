package id.go.blitarkab.bpbd.data.remote.service

import id.go.blitarkab.bpbd.data.remote.model.common.Wrapper
import id.go.blitarkab.bpbd.data.remote.model.response.*
import retrofit2.http.GET
import retrofit2.http.Query

interface DashboardService {

    @GET(value = "home")
    suspend fun getDashboardInfo(): Wrapper<DashboardResponse>

    @GET(value = "get_recent_posts")
    suspend fun getNewsList(): NewsResponse.Wrapper

    @GET(value = "news/info-earthquake")
    suspend fun getEarthQuakeDetail(): Wrapper<EarthQuakeResponse>

    @GET(value = "news/info-volcano")
    suspend fun getVolcanoList(): Wrapper<List<VolcanoResponse>>

    @GET(value = "news/info-weather")
    suspend fun getWeatherList(@Query(value = "time") time: Int): Wrapper<List<WeatherResponse>>

    @GET(value = "news/info-covid")
    suspend fun getCovidDetail(): Wrapper<CovidResponse>

}