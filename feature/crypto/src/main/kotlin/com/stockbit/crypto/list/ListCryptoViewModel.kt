package com.stockbit.crypto.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.baseapp.repository.crypto.repository.TopListRepository
import com.general.common.base.BaseViewModel
import com.model.crypto.crypto.ResponseListCryptoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCryptoViewModel @Inject constructor(
    private val topListRepository: TopListRepository
) : BaseViewModel() {

    val refresh = MutableLiveData<Boolean>()
    val listCrypto: LiveData<PagingData<ResponseListCryptoInfo>> = refresh.switchMap {
        topListRepository.getPagingTopTier().cachedIn(viewModelScope)
    }

    suspend fun isDataExist(): Boolean = topListRepository.checkDaoExist()

    fun refresh() {
        viewModelScope.launch {
            topListRepository.clearAllData()
            refresh.postValue(!(refresh.value ?: false))
        }
    }
}