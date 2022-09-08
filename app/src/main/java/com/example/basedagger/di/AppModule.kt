package com.example.basedagger.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.basedagger.base.BaseApplication
import com.example.basedagger.data.local.AppDatabase
import com.example.basedagger.data.repository.EmployeeRepository
import com.example.basedagger.data.repository.default.DefaultEmployeeRepository
import com.example.basedagger.data.source.dao.EmployeeDao
import com.example.basedagger.data.source.endpoint.EmployeeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideFavMovieDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "movie_db"
    ).build()

    @Singleton
    @Provides
    fun provideFavMovieDao(db: AppDatabase) = db.getFavoriteMovieDao()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        exampleDao: EmployeeDao,
        movieApi: EmployeeApi
    ) = DefaultEmployeeRepository(exampleDao, movieApi) as EmployeeRepository

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(provideOkHttpClientBasic(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun provideOkHttpClientBasic(basicAuth: String) = run {
        val okHttpClientBuilder = OkHttpClient.Builder()
//            .addInterceptor(provideHttpLoggingInterceptor())
//            .addInterceptor(provideCacheInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
            )
            .addInterceptor(
                ChuckerInterceptor.Builder(BaseApplication.applicationContext())
                    .collector(ChuckerCollector(BaseApplication.applicationContext()))
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
        okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): EmployeeApi =
        retrofit.create(EmployeeApi::class.java)
}