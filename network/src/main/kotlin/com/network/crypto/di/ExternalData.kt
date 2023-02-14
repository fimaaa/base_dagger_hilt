package com.network.crypto.di

import com.data.common.enum.EnumBuild
import com.network.crpyto.BuildConfig

external fun getDebugBaseUrl(): String
external fun getStagingBaseUrl(): String
external fun getReleaseBaseUrl(): String
external fun getSocketBaseUrlTopTier(): String
external fun getSocketBasicAuth(): String
external fun getSocketBaseUrlSocketTemp(): String

object ExternalData {
    init {
        System.loadLibrary("native-lib")
    }
}

fun getBaseUrl(): String =
    when (BuildConfig.BUILD_TYPE) {
        EnumBuild.RELEASE.name -> getReleaseBaseUrl()
        EnumBuild.STAGING.name -> getStagingBaseUrl()
        else -> getDebugBaseUrl()
    }

fun getSocketBaseUrlSocket() = getSocketBaseUrlSocketTemp()+getSocketBasicAuth()
