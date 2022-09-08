package com.example.basedagger.data.repository.default

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.basedagger.data.datasource.ExamplePagingSource
import com.example.basedagger.data.model.Employee
import com.example.basedagger.data.repository.EmployeeRepository
import com.example.basedagger.data.source.dao.EmployeeDao
import com.example.basedagger.data.source.endpoint.EmployeeApi
import javax.inject.Inject

class DefaultEmployeeRepository @Inject constructor(
    private val exampleDao: EmployeeDao,
    private val movieApi: EmployeeApi
) : EmployeeRepository {
    override suspend fun addToFavorite(example: Employee.Data) = exampleDao.addEmployee(example)
    override fun getFavoriteMovies() = exampleDao.getAllEmployee()
    override suspend fun checkMovie(id: String) = exampleDao.checkExample(id)
    override suspend fun removeFromFavorite(id: String) {
        exampleDao.removeExample(id)
    }

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
            pagingSourceFactory = { ExamplePagingSource(movieApi, search) }
        ).liveData
}