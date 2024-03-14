package com.network.crypto.crypto.service

import com.model.crypto.CryptoBaseResponse
import com.model.employee.Employee
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeApi {

    @GET("employees")
    suspend fun getEmployeesData(
//        @Path("search") search: String?
    ): CryptoBaseResponse<String, MutableList<Employee.Data>>

    @GET("employee/{search}")
    suspend fun getEmployeeData(
        @Path("search") search: String?
    ): CryptoBaseResponse<String, Employee.Data>
}