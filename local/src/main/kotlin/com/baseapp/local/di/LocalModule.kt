package com.baseapp.local.di

import android.content.Context
import com.baseapp.local.AppPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {
    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): AppPreference {
        return AppPreference(context)
    }
}