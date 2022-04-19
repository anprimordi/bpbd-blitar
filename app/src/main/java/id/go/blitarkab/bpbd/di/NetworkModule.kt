package id.go.blitarkab.bpbd.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.go.blitarkab.bpbd.data.remote.AppRemoteClient
import id.go.blitarkab.bpbd.data.remote.service.AccountService
import javax.inject.Qualifier

@Qualifier
annotation class FakeService

@Qualifier
annotation class LiveService

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @LiveService
    fun provideAccountService(appRemoteClient: AppRemoteClient): AccountService {
        return appRemoteClient.create(AccountService::class.java)
    }

}