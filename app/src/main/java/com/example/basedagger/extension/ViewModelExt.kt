package com.example.basedagger.extension

import androidx.lifecycle.viewModelScope
import com.example.basedagger.BuildConfig
import com.example.basedagger.base.BaseViewModel
import com.example.basedagger.data.base.Resource
import com.example.basedagger.data.base.UIText
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.net.ssl.HttpsURLConnection

fun <T> BaseViewModel.safeApiCall(
    callSuccess: suspend () -> T,
    callError: (UIText, Int) -> Unit = { _, _ -> }
) {
    statusViewModel.value = Resource.loading()
    viewModelScope.launch(Dispatchers.IO) {
        try {
            statusViewModel.postValue(Resource.success(true))
            callSuccess()
        } catch (t: Throwable) {
            val message = t.toThrowableMessage()
            val code = t.toThrowableCode()
            if (!BuildConfig.DEBUG) {
                val responseString = Gson().toJson("msg: $message} , code: $code")
                val url = t.ErrorResponseUrl()
                Firebase.crashlytics.log(url)
                Firebase.crashlytics.log(responseString)
                Firebase.crashlytics.recordException(t)
            }
            t.printStackTrace()
            when (code) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> statusViewModel.value = Resource.error(message, code)
//                null -> onError.postValue(AppError(ErrorType.INTERNET_ERROR, ""))
                else -> callError(message, code)
            }
        }
    }
}

fun <T> BaseViewModel.safeApiCallIndependent(
    callSuccess: suspend () -> T,
    callError: (Throwable) -> Unit = {}
) {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            callSuccess()
        } catch (t: Throwable) {
            if (!BuildConfig.DEBUG) {
                val message = t.toThrowableMessage()
                val code = t.toThrowableCode()
                val responseString = Gson().toJson("msg: $message} , code: $code")
                val url = t.ErrorResponseUrl()
                Firebase.crashlytics.log(url)
                Firebase.crashlytics.log(responseString)
                Firebase.crashlytics.recordException(t)
            }
            t.printStackTrace()
            callError(t)
        }
    }
}