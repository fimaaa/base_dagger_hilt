package com.basedagger.common.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.common.Resource
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + Dispatchers.IO

    private val mCompositeDisposable = CompositeDisposable()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean>
        get() = _isConnected
    fun setStatusConnection(isConnected: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _isConnected.emit(isConnected)
        }
    }

    private val _statusViewModel = MutableSharedFlow <Resource<Boolean>>(replay = 0)
    val statusViewModel:SharedFlow <Resource<Boolean>>
        get() = _statusViewModel
    fun setStatusViewModel(status: Resource<Boolean>) {
        viewModelScope.launch(Dispatchers.IO) {
            _statusViewModel.emit(status)
        }
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
        job.cancel()
    }
}