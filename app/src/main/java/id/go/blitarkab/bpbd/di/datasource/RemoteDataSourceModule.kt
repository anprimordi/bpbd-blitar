package id.go.blitarkab.bpbd.di.datasource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.go.blitarkab.bpbd.data.remote.source.AccountRemoteDataSource
import id.go.blitarkab.bpbd.data.remote.source.DashboardRemoteDataSource
import id.go.blitarkab.bpbd.data.remote.source.ReportRemoteDataSource
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.datasource.DashboardDataSource
import id.go.blitarkab.bpbd.domain.datasource.ReportDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @RemoteDataSource
    abstract fun bindAccountDataSource(accountRemoteDataSource: AccountRemoteDataSource): AccountDataSource

    @Binds
    @RemoteDataSource
    abstract fun bindDashboardDataSource(dashboardRemoteDataSource: DashboardRemoteDataSource): DashboardDataSource

    @Binds
    @RemoteDataSource
    abstract fun bindReportDataSource(reportRemoteDataSource: ReportRemoteDataSource): ReportDataSource

}