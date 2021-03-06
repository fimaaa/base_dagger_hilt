package com.example.basedagger.ui.employee

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.basedagger.data.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val repository: EmployeeRepository
) : ViewModel() {
    val search = MutableLiveData<String>()

    val exampleList = search.switchMap {
        repository.getExampleDataset(it).cachedIn(viewModelScope)
    }
}