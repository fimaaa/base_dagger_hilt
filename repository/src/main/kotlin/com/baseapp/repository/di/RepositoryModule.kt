package com.baseapp.repository.di

import com.baseapp.local.di.dao.CryptoDao
import com.baseapp.local.di.dao.EmployeeDao
import com.baseapp.local.di.dao.RemoteKeysDao
import com.baseapp.repository.repository.EmployeeRepository
import com.baseapp.repository.repository.EmployeeRepositoryImpl
import com.baseapp.repository.repository.TopListRepository
import com.baseapp.repository.repository.TopListRepositoryImpl
import com.network.crypto.datasource.TopListDataSource
import com.network.crypto.service.EmployeeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideDefaultEmployeeRepository(
        exampleDao: EmployeeDao,
        movieApi: EmployeeApi
    ) = EmployeeRepositoryImpl(exampleDao, movieApi) as EmployeeRepository

    @Singleton
    @Provides
    fun provideDefaultTopListRepository(
        datasource: TopListDataSource,
        dao: CryptoDao,
        remoteKeyDao: RemoteKeysDao
    ) = TopListRepositoryImpl(datasource, dao, remoteKeyDao) as TopListRepository
}