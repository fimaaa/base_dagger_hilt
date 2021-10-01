package com.example.basedagger.data.source.endpoint

import com.example.basedagger.data.base.BaseResponse
import com.example.basedagger.data.model.Employee
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeApi {

    @GET("employees")
    suspend fun getEmployeesData(
//        @Path("search") search: String?
    ): BaseResponse<MutableList<Employee.Data>>

    @GET("employee/{search}")
    suspend fun getEmployeeData(
        @Path("search") search: String?
    ): BaseResponse<Employee.Data>
}