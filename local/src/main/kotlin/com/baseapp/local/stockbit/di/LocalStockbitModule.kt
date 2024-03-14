package com.baseapp.local.stockbit.di

import android.content.Context
import androidx.room.Room
import com.baseapp.local.stockbit.dao.EmployeeDao
import com.baseapp.local.stockbit.StockBitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalStockbitModule {

    @Singleton
    @Provides
    fun provideFavMovieDatabase(
        @ApplicationContext app: Context
    ): StockBitDatabase = Room.databaseBuilder(
        app,
        StockBitDatabase::class.java,
        "base_app_db"
    ).build()

    @Singleton
    @Provides
    fun provideEmployeeDao(db: StockBitDatabase): EmployeeDao = db.getEmployeeDao()

    @Singleton
    @Provides
    fun provideRepDao(db: StockBitDatabase) = db.getRepoDao()

    @Singleton
    @Provides
    fun provideCryptoDao(db: StockBitDatabase) = db.cryptoDao()
}