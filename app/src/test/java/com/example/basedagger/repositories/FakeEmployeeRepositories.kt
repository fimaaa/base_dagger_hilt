package com.example.basedagger.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.basedagger.data.datasource.ExamplePagingSource
import com.example.basedagger.data.model.Employee
import com.example.basedagger.data.repository.EmployeeRepository
import com.example.basedagger.source.endpoint.FakeEmployeeApi

class FakeEmployeeRepositories : EmployeeRepository {

    private val observableEmployeeData = MutableLiveData<MutableList<Employee.Data>>()

    private val fakeEmployeeApi = FakeEmployeeApi()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
        fakeEmployeeApi.setShouldReturnNetworkError(value)
    }

    override suspend fun addToFavorite(example: Employee.Data) {
        TODO("Not yet implemented")
    }

    override fun getFavoriteMovies(): LiveData<MutableList<Employee.Data>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkMovie(id: String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavorite(id: String) {
        TODO("Not yet implemented")
    }

    override fun getExampleDataset(search: String?): LiveData<PagingData<Employee.Data>> =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                prefetchDistance = 10
            ),
            pagingSourceFactory = { ExamplePagingSource(fakeEmployeeApi, search) }
        ).liveData
}