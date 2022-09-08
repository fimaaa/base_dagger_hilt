package com.example.basedagger.base

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basedagger.data.base.Resource
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + Dispatchers.IO

    private val mCompositeDisposable = CompositeDisposable()
    val statusViewModel = MutableLiveData<Resource<Boolean>>()

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
        job.cancel()
    }

    var compositeDisposable = mCompositeDisposable

    lateinit var navigator: BaseNavigator
}