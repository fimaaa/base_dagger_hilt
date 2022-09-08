package com.example.basedagger.data.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.basedagger.data.model.Employee

@Dao
interface EmployeeDao {
    @Insert
    fun addEmployee(example: Employee.Data)

    @Query("SELECT * FROM employee_table")
    fun getAllEmployee(): LiveData<MutableList<Employee.Data>>

    @Query("SELECT count(*) FROM employee_table WHERE employee_table.id = :id")
    fun checkExample(id: String): Int

    @Query("DELETE FROM employee_table WHERE employee_table.id = :id")
    fun removeExample(id: String): Int
}