package id.go.blitarkab.bpbd.data.remote.source

import id.go.blitarkab.bpbd.data.remote.AppRemoteClient
import id.go.blitarkab.bpbd.data.remote.NewsRemoteClient
import id.go.blitarkab.bpbd.data.remote.service.DashboardService
import id.go.blitarkab.bpbd.data.remote.util.RemoteDateTimeUtils
import id.go.blitarkab.bpbd.data.remote.util.execute
import id.go.blitarkab.bpbd.domain.datasource.DashboardDataSource
import id.go.blitarkab.bpbd.domain.model.*
import id.go.blitarkab.bpbd.domain.model.common.AuthError
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DashboardRemoteDataSource @Inject constructor(
    private val remoteClient: AppRemoteClient,
    private val newsClient: NewsRemoteClient,
) : DashboardDataSource {

    override suspend fun getDashboardInfo(): Result<Dashboard> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(DashboardService::class.java)
                service.getDashboardInfo()
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else AuthError()
            }
        }
    }

    override suspend fun getCovidInfoDetail(): Result<Pair<Covid, List<Covid>>> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(DashboardService::class.java)
                service.getCovidDetail()
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) {
                    val country = response.data.country?.toDomain() ?: Covid.DEFAULT
                    val provinces =
                        response.data.province?.map { it.province?.toDomain() ?: Covid.DEFAULT }
                            ?: emptyList()
                    Success(Pair(country, provinces))
                } else AuthError()
            }
        }
    }

    override suspend fun getEarthQuakeDetail(): Result<EarthQuake> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(DashboardService::class.java)
                service.getEarthQuakeDetail()
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else AuthError()
            }
        }
    }

    override suspend fun getVolcanoList(): Result<List<Volcano>> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(DashboardService::class.java)
                service.getVolcanoList()
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.map { it.toDomain() })
                else AuthError()
            }
        }
    }

    override suspend fun getNewsList(): Result<List<News>> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = newsClient.create(DashboardService::class.java)
                service.getNewsList()
            }.mapTo { response ->
                if (response.data != null) Success(response.data.map { it.toDomain() })
                else AuthError()
            }
        }
    }

    override suspend fun getWeatherList(): Result<List<Weather>> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(DashboardService::class.java)
                val time = RemoteDateTimeUtils.getCurrentTimeForWeather()
                service.getWeatherList(time)
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.map { it.toDomain() })
                else AuthError()
            }
        }
    }

}