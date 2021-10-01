package com.example.basedagger.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.basedagger.data.model.Employee
import com.example.basedagger.data.source.dao.EmployeeDao

@Database(
    entities = [Employee.Data::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFavoriteMovieDao(): EmployeeDao
}