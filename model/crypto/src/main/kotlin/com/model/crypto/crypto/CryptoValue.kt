package com.model.crypto.crypto

import com.google.gson.annotations.SerializedName

data class CryptoValue (
    @SerializedName("PRICE")
    val price: String = "",

    @SerializedName("TOTALTOPTIERVOLUME24H")
    var change: Double = 0.0
)