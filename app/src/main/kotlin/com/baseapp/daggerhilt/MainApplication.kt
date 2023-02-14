package com.baseapp.daggerhilt

import com.basedagger.common.base.BaseApplication
import com.network.crypto.di.ExternalData
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        ExternalData
    }
}