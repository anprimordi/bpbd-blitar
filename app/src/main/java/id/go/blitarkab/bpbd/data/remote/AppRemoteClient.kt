package id.go.blitarkab.bpbd.data.remote

import android.content.Context
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import id.go.blitarkab.bpbd.BuildConfig
import id.go.blitarkab.bpbd.data.local.preference.AppPreference
import id.go.blitarkab.bpbd.data.local.preference.AppPreference.Companion.KEY_ACCOUNT
import id.go.blitarkab.bpbd.data.remote.interceptor.Authorization
import id.go.blitarkab.bpbd.domain.model.Account
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRemoteClient @Inject constructor(
    @ApplicationContext context: Context,
    private val preference: AppPreference
) {

    companion object {
        const val API_URL = "${BuildConfig.SERVER_URL}/api/"
    }

    private val chuck = ChuckInterceptor(context)
    private val gson = GsonBuilder().setLenient().create()

    fun <T> create(clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(buildClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(clazz)
    }

    private fun buildClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.BASIC

        val builder = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(chuck)

        val accountJson = preference.get().getString(KEY_ACCOUNT, null)
        if (accountJson != null) {
            try {
                val account =
                    gson.fromJson(accountJson, Account::class.java) ?: throw NullPointerException()
                builder.addInterceptor(Authorization(account.token))
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        return builder.build()
    }

}