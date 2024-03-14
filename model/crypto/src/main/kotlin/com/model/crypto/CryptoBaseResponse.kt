package com.model.crypto

import com.data.common.BasePagination
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

/**
 * Generic data class for parsing response
 * M for message type string, D for data type any
 */

data class CryptoBaseResponse<M, D>(
    @Json(name = "Message")
    @SerializedName(value = "Message")
    private val codeMessageCrypto: M,
    @Json(name = "Type")
    @SerializedName(value = "Type")
    private val codeCrypto: Int?,
    @Json(name = "Data")
    @SerializedName(value = "Data")
    private val dataCrypto: D,
    @Json(name = "Metadata")
    @SerializedName(value = "Metadata")
    private val paginationCrypto: BasePagination? = null,

    @Json(name = "message")
    @SerializedName(value = "message")
    private val codeMessageEmployee: M?,
    @Json(name = "type")
    @SerializedName(value = "type")
    private val codeEmployee: Int?,
    @Json(name = "data")
    @SerializedName(value = "data")
    private val dataEmployee: D?,
    @Json(name = "metadata")
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