package com.baseapp.local.member.di

import android.content.Context
import androidx.room.Room
import com.baseapp.local.member.MemberDatabase
import com.baseapp.local.member.dao.MemberDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalMemberModule {

    @Singleton
    @Provides
    fun provideTestDanamonDatabase(
        @ApplicationContext app: Context
    ): MemberDatabase = Room.databaseBuilder(
        app,
        MemberDatabase::class.java,
        "general_test_danamon"
    ).build()

    @Singleton
    @Provides
    fun provideMemberDao(db: MemberDatabase): MemberDao = db.getMemberDao()
}