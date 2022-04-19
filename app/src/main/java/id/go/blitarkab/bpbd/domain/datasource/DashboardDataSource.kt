package id.go.blitarkab.bpbd.domain.datasource

import id.go.blitarkab.bpbd.domain.model.*
import id.go.blitarkab.bpbd.domain.model.common.Result

interface DashboardDataSource {

    suspend fun getDashboardInfo(): Result<Dashboard>
    suspend fun getCovidInfoDetail(): Result<Pair<Covid, List<Covid>>>
    suspend fun getEarthQuakeDetail(): Result<EarthQuake>
    suspend fun getVolcanoList(): Result<List<Volcano>>
    suspend fun getNewsList(): Result<List<News>>
    suspend fun getWeatherList(): Result<List<Weather>>

}