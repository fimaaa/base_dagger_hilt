package com.feature.employee.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.baseapp.repository.repository.EmployeeRepository
import com.basedagger.common.base.BaseViewModel
import com.model.employee.Employee
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