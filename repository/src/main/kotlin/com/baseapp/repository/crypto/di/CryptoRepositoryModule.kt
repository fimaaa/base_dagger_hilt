package com.baseapp.repository.crypto.di

import com.baseapp.repository.crypto.repository.EmployeeRepository
import com.baseapp.repository.crypto.repository.EmployeeRepositoryImpl
import com.baseapp.repository.crypto.repository.TopListRepository
import com.baseapp.repository.crypto.repository.TopListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class, ViewModelComponent::class)
@Module
interface CryptoRepositoryModule {
    @Binds
    fun bindsEmployeeRepository(
        employeeRepositoryRepositoryImpl: EmployeeRepositoryImpl
    ): EmployeeRepository

    @Binds
    fun bindsTopListRepository(
        topListRepositoryImpl: TopListRepositoryImpl
    ): TopListRepository
}