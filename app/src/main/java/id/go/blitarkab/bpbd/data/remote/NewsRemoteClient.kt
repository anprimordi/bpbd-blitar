package id.go.blitarkab.bpbd.data.remote

import android.content.Context
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import id.go.blitarkab.bpbd.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRemoteClient @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        const val API_URL = "https://bpbd.blitarkab.go.id/api/"
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

        return builder.build()
    }

}