package com.basedagger.common.extension

import androidx.lifecycle.viewModelScope
import com.basedagger.common.base.BaseViewModel
import com.data.common.Resource
import com.data.common.UIText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun <T> BaseViewModel.safeApiCall(callSuccess: suspend () -> T) {
    if(isConnected.value) {
        setStatusViewModel(Resource.error(UIText.DynamicString(""), null))
    } else {
        setStatusViewModel(Resource.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setStatusViewModel(Resource.success(true))
                callSuccess()
            } catch (t: Throwable) {
                if(isConnected.value) {
                    setStatusViewModel(Resource.error(UIText.DynamicString(""), null))
                } else {
                    val message = t.toThrowableMessage()
                    val code = t.toThrowableCode()
                    t.printStackTrace()
                    setStatusViewModel(Resource.error(message, code))
                }
            }
        }
    }
}

fun <T> BaseViewModel.safeApiCallIndependent(
    callSuccess: suspend () -> T,
    callError: (Throwable) -> Unit = {}
) {
    if(isConnected.value) {
       callError(Throwable("No Internet Connection"))
    } else {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                callSuccess()
            } catch (t: Throwable) {
                t.printStackTrace()
                callError(t)
            }
        }
    }
}