package com.baseapp.local.member

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.baseapp.local.member.dao.MemberDao
import com.general.model.common.user.MemberLocal

@Database(
    entities = [
        MemberLocal::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(MemberConverter::class)
abstract class MemberDatabase : RoomDatabase() {
    // DAO
    abstract fun getMemberDao(): MemberDao
}