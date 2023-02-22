package com.data.common

import com.google.gson.annotations.SerializedName

/**
 * Generic data class for parsing response
 * M for message type string, D for data type any
 */

data class BaseResponse<M, D>(
    @SerializedName(value = "Message")
    private val codeMessageCrypto: M,
    @SerializedName(value = "Type")
    private val codeCrypto: Int?,
    @SerializedName(value = "Data")
    private val dataCrypto: D,
    @SerializedName(value = "Metadata")
    private val paginationCrypto: BasePagination? = null,

    @SerializedName(value = "message")
    private val codeMessageEmployee: M,
    @SerializedName(value = "type")
    private val codeEmployee: Int?,
    @SerializedName(value = "data")
    private val dataEmployee: D,
    @SerializedName(value = "metadata")
    private val paginationEmployee: BasePagination? = null

) {
    val codeMessage: M
        get() = codeMessageEmployee ?: codeMessageCrypto
    val code: Int
        get() = codeEmployee ?: codeCrypto ?: -1
    val data: D
        get() = dataEmployee ?: dataCrypto
    val pagination: BasePagination?
        get() = paginationEmployee ?: paginationCrypto
}