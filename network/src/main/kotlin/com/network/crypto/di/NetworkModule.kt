package com.network.crypto.di

import android.content.Context
import android.os.Build
import com.baseapp.local.AppPreference
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.network.crypto.auth.service.AuthService
import com.network.crpyto.BuildConfig
import com.network.crypto.TokenAuthenticator
import com.network.crypto.adapter.JSONObjectAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.json.JSONObject
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun clientBasic(): String = ""

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(JSONObjectAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun providesMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    @Named("interceptor_chucker")
    fun provideChuckerInterceptor(
        @ApplicationContext mContext: Context
    ): Interceptor = ChuckerInterceptor.Builder(mContext)
        .collector(ChuckerCollector(mContext))
        .maxContentLength(250000L)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(true)
        .build()

    @Provides
    @Singleton
    @Named("client_basic")
    fun provideOkHttpClientBasic(
        basicAuth: String,
        @Named("interceptor_chucker") interceptorChucker: Interceptor
    ): OkHttpClient = run {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val language = if (Locale.getDefault().language == "in") "id" else "en"
                val request = chain.request()
                val requestBuilder = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept-Language", language)
                    .addHeader("X-version", BuildConfig.VERSION_CODE.toString())
                if (basicAuth.isNotEmpty() && request.header("Authorization") == null) {
                    requestBuilder.addHeader(
                        "Authorization", basicAuth
                    )
                }
                chain.proceed(requestBuilder.build())
            }
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
            )
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(interceptorChucker)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
        okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        appPreference: AppPreference,
        authRepository: AuthService
    ): TokenAuthenticator {
        return TokenAuthenticator(appPreference, authRepository)
    }

    @Provides
    @Singleton
    @Named("client_auth")
    fun provideOkHttpClientAuth(
        appPreference: AppPreference,
        tokenAuthenticator: TokenAuthenticator,
        @Named("interceptor_chucker") interceptorChucker: Interceptor
    ): OkHttpClient = run {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val language = if (Locale.getDefault().language == "in") "id" else "en"
                val token: String = "Bearer " + runBlocking { appPreference.readToken() ?: "" }
                val request = chain.request()
                val requestBuilder = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept-Language", language)
                    .addHeader("X-version", BuildConfig.VERSION_CODE.toString())
                if (token.isNotEmpty() && request.header("Authorization") == null) {
                    requestBuilder.addHeader(
                        "Authorization", token
                    )
                }
                chain.proceed(requestBuilder.build())
            }
            .authenticator(tokenAuthenticator)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
            )
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(interceptorChucker)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
        okHttpClientBuilder.build()
    }

    private fun bodyToString(request: RequestBody?): String? {
        return try {
            val buffer = Buffer()
            if (request != null) request.writeTo(buffer) else return ""
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }

    private fun getBody(
        intention: String,
        body: RequestBody? = null,
        deviceId: String,
        token: String = "",
        parameter: String? = null
    ): RequestBody {
        val versionName = BuildConfig.VERSION_CODE.toString()
        val formBody = JSONObject()
        formBody.put("client_name", "test danamon")

        formBody.put("version", versionName.split("-").first())
        formBody.put("app_name", "test danamon")
        formBody.put("query_param", "")
        formBody.put("authorization_forward", token)

        val headerPayload = JSONObject()
        headerPayload.put("Content-Type", "application/json")
        headerPayload.put("X-Version", versionName)
        headerPayload.put("Device-Id", deviceId)
        headerPayload.put("Device-Type", Build.MODEL)
        formBody.put("headers", headerPayload)

        try {
            if (body != null) {
                val customReq = bodyToString(body)
                if (!customReq.isNullOrEmpty()) {
                    val newBody = JSONObject(customReq)
                    formBody.put("payload", newBody)
                }
            }
            formBody.put("intention", intention)

            formBody.put("query_param", parameter)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return formBody.toString().toRequestBody(body?.contentType())
    }
}