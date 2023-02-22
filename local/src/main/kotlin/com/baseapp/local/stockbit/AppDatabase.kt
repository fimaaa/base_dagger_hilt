package com.baseapp.local.stockbit

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.baseapp.local.di.dao.CryptoDao
import com.baseapp.local.di.dao.EmployeeDao
import com.baseapp.local.di.dao.RemoteKeysDao
import com.baseapp.local.stockbit.converter.Converters
import com.model.crypto.crypto.RemoteKeys
import com.model.crypto.crypto.ResponseListCryptoInfo
import com.model.employee.Employee

@Database(
    entities = [ResponseListCryptoInfo::class, RemoteKeys::class, Employee.Data::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    // DAO
    abstract fun getRepoDao(): RemoteKeysDao
    abstract fun cryptoDao(): CryptoDao
    abstract fun getEmployeeDao(): EmployeeDao
}