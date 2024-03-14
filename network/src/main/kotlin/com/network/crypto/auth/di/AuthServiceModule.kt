package com.network.crypto.auth.di

import com.network.crypto.auth.service.AuthService
import com.network.crypto.auth.service.MemberService
import com.network.crypto.di.getBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthServiceModule {
    @Provides
    @Singleton
    @Named("auth")
    fun provideRetrofitAuth(
        @Named("client_basic") okHttpClient: OkHttpClient,
        converterFactory: MoshiConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideAuthService(@Named("auth") retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    @Named("memberJwt")
    fun provideRetrofitMember(
        @Named("client_auth") okHttpClient: OkHttpClient,
        converterFactory: MoshiConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideMemberService(@Named("memberJwt") retrofit: Retrofit): MemberService =
        retrofit.create(MemberService::class.java)
}