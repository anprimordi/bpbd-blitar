package id.go.blitarkab.bpbd.di.datasource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.go.blitarkab.bpbd.data.local.source.AccountLocalDataSource
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindAccountDataSource(accountLocalDataSource: AccountLocalDataSource): AccountDataSource

}