package com.example.basedagger.ui.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.basedagger.base.BaseViewModel
import com.example.basedagger.data.model.Employee
import com.example.basedagger.data.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val repository: EmployeeRepository
) : BaseViewModel() {
    val search = MutableLiveData<String>()

    val exampleList: LiveData<PagingData<Employee.Data>> = search.switchMap {
        repository.getExampleDataset(it).cachedIn(viewModelScope)
    }
}