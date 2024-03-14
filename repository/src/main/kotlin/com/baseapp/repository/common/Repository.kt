package com.baseapp.repository.common

import com.data.common.UIText
import com.data.common.ViewState
interface Repository {
    val suspendOnExceptionError: ViewState.ERROR<Nothing>
        get() = ViewState.ERROR(msg = UIText.DynamicString("Terjadi kesalahan"), code = 502)
}