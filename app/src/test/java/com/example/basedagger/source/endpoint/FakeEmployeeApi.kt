package com.example.basedagger.source.endpoint

import com.example.basedagger.data.base.BaseResponse
import com.example.basedagger.data.model.Employee
import com.example.basedagger.data.source.endpoint.EmployeeApi

class FakeEmployeeApi : EmployeeApi {
    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getEmployeesData(): BaseResponse<MutableList<Employee.Data>> = if (shouldReturnNetworkError) {
        BaseResponse(
            status = false,
            data = mutableListOf(),
            message = "Error",
            statusCode = 400,
            meta = null
        )
    } else {
        BaseResponse(
            status = true,
            data = mutableListOf(
                Employee.Data(
                    id = "0",
                    employee_name = "0",
                    employee_salary = 0.0,
                    employee_age = "",
                    profile_image = ""
                ),
                Employee.Data(
                    id = "1",
                    employee_name = "1",
                    employee_salary = 0.0,
                    employee_age = "",
                    profile_image = ""
                )
            ),
            message = "success",
            statusCode = 200,
            meta = null
        )
    }

    override suspend fun getEmployeeData(search: String?): BaseResponse<Employee.Data> = if (shouldReturnNetworkError) {
        BaseResponse(
            status = false,
            data = Employee.Data(
                id = "0",
                employee_name = "",
                employee_salary = 0.0,
                employee_age = "",
                profile_image = ""
            ),
            message = "Error",
            statusCode = 400,
            meta = null
        )
    } else {
        if (search.isNullOrEmpty()) {
            BaseResponse(
                status = false,
                data = Employee.Data(
                    id = "0",
                    employee_name = "",
                    employee_salary = 0.0,
                    employee_age = "",
                    profile_image = ""
                ),
                message = "Not Found",
                statusCode = 400,
                meta = null
            )
        } else {
            BaseResponse(
                status = true,
                data = Employee.Data(
                    id = "0",
                    employee_name = "",
                    employee_salary = 0.0,
                    employee_age = "",
                    profile_image = ""
                ),
                message = "success",
                statusCode = 200,
                meta = null
            )
        }
    }
}