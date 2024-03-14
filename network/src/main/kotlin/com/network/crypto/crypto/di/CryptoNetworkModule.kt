package com.network.crypto.crypto.di

import com.network.crypto.crypto.service.EmployeeApi
import com.network.crypto.crypto.service.TopListServices
import com.network.crypto.di.getBaseUrl
import com.network.crypto.di.getSocketBaseUrlSocket
import com.network.crypto.di.getSocketBaseUrlTopTier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URI
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CryptoNetworkModule {
    @Provides
    @Singleton
    @Named("Socket_TopList")
    fun provideRetrofitSocketTopList(
        @Named("client_basic") okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(getSocketBaseUrlTopTier())
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideTopListServices(@Named("Socket_TopList") retrofit: Retrofit): TopListServices =
        retrofit.create(TopListServices::class.java)

    @Provides
    @Singleton
    @Named("employee")
    fun provideRetrofitEmployee(
        @Named("client_basic") okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideMovieApi(@Named("employee") retrofit: Retrofit): EmployeeApi =
        retrofit.create(EmployeeApi::class.java)

    @Singleton
    @Provides
    fun provideWebSocketURI(): URI = URI(getSocketBaseUrlSocket())
}