package id.go.blitarkab.bpbd.data

import id.go.blitarkab.bpbd.di.datasource.RemoteDataSource
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.DashboardDataSource
import id.go.blitarkab.bpbd.domain.model.*
import id.go.blitarkab.bpbd.domain.model.common.Result
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    @RemoteDataSource private val remoteSource: DashboardDataSource
) : DashboardDataSource {

    override suspend fun getDashboardInfo(): Result<Dashboard> {
        return remoteSource.getDashboardInfo()
    }

    override suspend fun getCovidInfoDetail(): Result<Pair<Covid, List<Covid>>> {
        return remoteSource.getCovidInfoDetail()
    }

    override suspend fun getEarthQuakeDetail(): Result<EarthQuake> {
        return remoteSource.getEarthQuakeDetail()
    }

    override suspend fun getVolcanoList(): Result<List<Volcano>> {
        return remoteSource.getVolcanoList()
    }

    override suspend fun getNewsList(): Result<List<News>> {
        return remoteSource.getNewsList()
    }

    override suspend fun getWeatherList(): Result<List<Weather>> {
        return remoteSource.getWeatherList()
    }

}