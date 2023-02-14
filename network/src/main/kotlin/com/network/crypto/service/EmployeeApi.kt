package com.network.crypto.service

import com.data.common.BaseResponse
import com.model.employee.Employee
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeApi {

    @GET("employees")
    suspend fun getEmployeesData(
//        @Path("search") search: String?
    ): BaseResponse<String, MutableList<Employee.Data>>

    @GET("employee/{search}")
    suspend fun getEmployeeData(
        @Path("search") search: String?
    ): BaseResponse<String, Employee.Data>
}