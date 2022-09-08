package com.example.basedagger.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.basedagger.data.model.Employee

interface EmployeeRepository {
    suspend fun addToFavorite(example: Employee.Data)

    fun getFavoriteMovies(): LiveData<MutableList<Employee.Data>>

    suspend fun checkMovie(id: String): Int

    suspend fun removeFromFavorite(id: String)

    fun getExampleDataset(
        search: String?
    ): LiveData<PagingData<Employee.Data>>
}