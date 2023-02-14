package com.baseapp.repository.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.baseapp.local.di.dao.EmployeeDao
import com.model.employee.Employee
import com.network.crypto.service.EmployeeApi
import com.baseapp.repository.datasource.EmployeePagingSource
import javax.inject.Inject

interface EmployeeRepository {
    suspend fun addToFavorite(example: Employee.Data)

//    suspend fun getFavoriteMovies(): List<Employee.Data>

//    suspend fun checkMovie(id: String): Int
//
//    suspend fun removeFromFavorite(id: String)

    fun getExampleDataset(
        search: String?
    ): LiveData<PagingData<Employee.Data>>
}

class EmployeeRepositoryImpl @Inject constructor(
    private val exampleDao: EmployeeDao,
    private val movieApi: EmployeeApi
) : EmployeeRepository {
    override suspend fun addToFavorite(example: Employee.Data) = exampleDao.save(example)
//    override suspend fun getFavoriteMovies() = exampleDao.getAllEmployee()
//    override suspend fun checkMovie(id: String) = exampleDao.checkExample(id)
//    override suspend fun removeFromFavorite(id: String) {
//        exampleDao.removeExample(id)
//    }

    override fun getExampleDataset(
        search: String?
    ) =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                prefetchDistance = 10
            ),
            pagingSourceFactory = { EmployeePagingSource(movieApi, search) }
        ).liveData
}