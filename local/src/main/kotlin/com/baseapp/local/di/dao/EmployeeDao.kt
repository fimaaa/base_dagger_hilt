package com.baseapp.local.di.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.baseapp.local.common.dao.BaseDao
import com.model.employee.Employee

@Dao
interface EmployeeDao : BaseDao<Employee.Data> {

    @Query("SELECT * FROM employee_table")
    suspend fun getAllEmployee(): List<Employee.Data>

    @Query("SELECT count(*) FROM employee_table WHERE employee_table.id = :id")
    suspend fun checkExample(id: String): Int

    @Query("DELETE FROM employee_table WHERE employee_table.id = :id")
    suspend fun removeExample(id: String)

    @Query("SELECT * FROM employee_table ORDER BY employee_table.id ASC")
    fun getListEmployeePagination(): PagingSource<Int, Employee.Data>

    suspend fun save(data: Employee.Data) {
        insert(data)
    }

    suspend fun save(data: List<Employee.Data>) {
        insert(data)
    }
}