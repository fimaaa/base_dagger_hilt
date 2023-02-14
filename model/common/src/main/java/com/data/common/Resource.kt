package com.data.common

import com.data.common.enum.Status
import com.model.common.R
import java.net.HttpURLConnection

data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val getMessage: UIText? = null,
    val code: Int? = null,
    val throwable: Throwable? = null
) {
    val message: UIText
        get() = getMessage
            ?: when (status) {
                Status.SUCCESS -> UIText.StringResource(R.string.success_default)
                Status.EMPTY -> UIText.StringResource(R.string.error_search_notfound)
                Status.LOADING -> UIText.StringResource(R.string.loading_default)
                Status.ERROR -> UIText.StringResource(R.string.error_default)
            }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                data = data
            )
        }

        fun <T> error(msg: UIText?, code: Int? = null, data: T? = null, err: Throwable? = null): Resource<T> {
            return Resource(
                status = Status.ERROR,
                data = data,
                getMessage = msg,
                code = code,
                throwable = err)
        }

        fun <T> loading(data: T? = null): Resource<T> = Resource(
            status = Status.LOADING,
            data = data
        )

        fun <T> empty(msg: UIText?): Resource<T> = Resource(
            status = Status.EMPTY,
            getMessage = null,
            code = HttpURLConnection.HTTP_NO_CONTENT
        )
    }
}