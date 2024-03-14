package com.baseapp.repository.auth.di

import com.baseapp.repository.auth.repository.SessionRepository
import com.baseapp.repository.auth.repository.SessionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class, ViewModelComponent::class)
@Module
interface AuthRepositoryModule {
    @Binds
    fun bindsSessionsRepository(
        sessionRepositoryImpl: SessionRepositoryImpl
    ): SessionRepository
}