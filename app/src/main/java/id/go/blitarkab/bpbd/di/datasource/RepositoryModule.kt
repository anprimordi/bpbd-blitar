package id.go.blitarkab.bpbd.di.datasource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.go.blitarkab.bpbd.data.AccountRepository
import id.go.blitarkab.bpbd.data.DashboardRepository
import id.go.blitarkab.bpbd.data.ReportRepository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.datasource.DashboardDataSource
import id.go.blitarkab.bpbd.domain.datasource.ReportDataSource
import javax.inject.Qualifier

@Qualifier
annotation class Repository

@Qualifier
annotation class LocalDataSource

@Qualifier
annotation class RemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Repository
    abstract fun bindAccountDataSource(accountRepository: AccountRepository): AccountDataSource

    @Binds
    @Repository
    abstract fun bindDashboardDataSource(dashboardRepository: DashboardRepository): DashboardDataSource

    @Binds
    @Repository
    abstract fun bindReportDataSource(reportRepository: ReportRepository): ReportDataSource

}