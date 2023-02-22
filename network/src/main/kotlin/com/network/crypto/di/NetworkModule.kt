package com.network.crypto.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.network.crypto.service.EmployeeApi
import com.network.crypto.service.TopListServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URI
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    @Named("Socket_TopList")
    fun provideRetrofitSocketTopList(
        @ApplicationContext mContext: Context
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(getSocketBaseUrlTopTier())
            .client(provideOkHttpClientBasic(mContext, ""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("employee")
    fun provideRetrofitEmployee(
        @ApplicationContext mContext: Context
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(provideOkHttpClientBasic(mContext, ""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun provideOkHttpClientBasic(
        mContext: Context,
        basicAuth: String
    ) = run {
        val okHttpClientBuilder = OkHttpClient.Builder()
//            .addInterceptor(provideHttpLoggingInterceptor())
//            .addInterceptor(provideCacheInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
            )
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(
                ChuckerInterceptor.Builder(mContext)
                    .collector(ChuckerCollector(mContext))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
            .addInterceptor { chain ->
                val language = if (Locale.getDefault().language == "in") "id" else "en"
                val request = chain.request()
                val requestBuilder = request.newBuilder()
                    .addHeader(
                        "Authorization", basicAuth
                    )
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept-Language", language)
                    .build()
                chain.proceed(requestBuilder)
            }
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
        println("TAG okHTTP ${okHttpClientBuilder.interceptors()}")
        okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideTopListServices(@Named("Socket_TopList") retrofit: Retrofit): TopListServices =
        retrofit.create(TopListServices::class.java)

    @Provides
    @Singleton
    fun provideMovieApi(@Named("employee") retrofit: Retrofit): EmployeeApi =
        retrofit.create(EmployeeApi::class.java)

    @Singleton
    @Provides
    fun provideWebSocketURI(): URI = URI(getSocketBaseUrlSocket())
}