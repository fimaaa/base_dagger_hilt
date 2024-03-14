package com.example.admin.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.baseapp.repository.auth.repository.SessionRepository
import com.general.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListUserViewModel @Inject constructor(
    sessionRepository: SessionRepository
) : BaseViewModel() {
    private val _refreshList = MutableLiveData<Boolean>()
    private val refreshList: LiveData<Boolean>
        get() = _refreshList

    fun refreshData() {
        _refreshList.value = !(refreshList.value ?: false)
    }

    val listJsonPhoto = refreshList.switchMap {
        sessionRepository.showListMemberPaging().cachedIn(viewModelScope)
    }
}