package com.example.basedagger.data.base

import com.example.basedagger.data.model.Meta
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

    @SerializedName("status")
    val status: Boolean = false,

    @SerializedName("data")
    val data: T,

    @SerializedName("message")
    val message: String = "Not Found",

    @SerializedName("statusCode")
    val statusCode: Int = 404,

    @SerializedName("meta")
    val meta: Meta? = null
)