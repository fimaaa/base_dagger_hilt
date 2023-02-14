package com.data.common

import com.google.gson.annotations.SerializedName

/**
 * Generic data class for parsing response
 * M for message type string, D for data type any
 */

data class BaseResponse<M,D>(
    @SerializedName(value = ",essage")
    val codeMessage: M,
    @SerializedName(value = "Type")
    val code: Int = 0,
    @SerializedName(value = "data")
    val data: D,
    @SerializedName(value = "metadata")
    val pagination: BasePagination? = null
)