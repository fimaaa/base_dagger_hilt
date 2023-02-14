package com.baseapp.local.di

import android.content.Context
import androidx.room.Room
import com.baseapp.local.di.dao.EmployeeDao
import com.baseapp.local.stockbit.AppDatabase
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
    fun provideFavMovieDatabase(
        @ApplicationContext app: Context
    ): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "base_app_db"
    ).build()

    @Singleton
    @Provides
    fun provideEmployeeDao(db: AppDatabase): EmployeeDao = db.getEmployeeDao()

    @Singleton
    @Provides
    fun provideRepDao(db: AppDatabase) = db.getRepoDao()

    @Singleton
    @Provides
    fun provideCryptoDao(db: AppDatabase) = db.cryptoDao()
}