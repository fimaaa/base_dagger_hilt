package com.example.basedagger.di

import com.example.basedagger.BuildConfig
import com.example.basedagger.data.enum.EnumBuild

external fun getDebugBaseUrl(): String
external fun getStagingBaseUrl(): String
external fun getReleaseBaseUrl(): String

object ExternalData {
    init {
        System.loadLibrary("native-lib")
    }
}

fun getBaseUrl(): String =
    when (BuildConfig.VARIANT) {
        EnumBuild.RELEASE.name -> getReleaseBaseUrl()
        EnumBuild.STAGING.name -> getStagingBaseUrl()
        else -> getDebugBaseUrl()
    }