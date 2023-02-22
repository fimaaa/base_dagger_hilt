package com.basedagger.common.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { action.invoke(it) })
}

fun <T> Fragment.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer { action.invoke(it) })
}

fun <T> AppCompatActivity.safeCollect(flow: Flow<T>, action: (t: T) -> Unit) {
    lifecycleScope.launch(Dispatchers.Main) {
        flow.safeCollect { action.invoke(it) }
    }
}

fun <T> Fragment.safeCollect(flow: Flow<T>, action: (t: T) -> Unit) {
    lifecycleScope.launch(Dispatchers.Main) {
        flow.safeCollect { action.invoke(it) }
    }
}

fun <T> FragmentActivity.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { action.invoke(it) })
}

fun <T> FragmentActivity.safeCollect(flow: Flow<T>, action: (t: T) -> Unit) {
    lifecycleScope.launch(Dispatchers.Main) {
        flow.safeCollect { action.invoke(it) }
    }
}
suspend inline fun <T> Flow<T>.safeCollect(crossinline action: suspend (T) -> Unit) {
    collect {
        coroutineContext.ensureActive()
        action(it)
    }
}