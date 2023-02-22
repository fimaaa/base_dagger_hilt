package com.model.crypto.crypto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ResponseListCryptoInfo(
    @PrimaryKey
    var id: Int = 0,

    var page: Int = 0,

    @SerializedName("CoinInfo")
    val coinInfo: CryptoData = CryptoData(),

    @SerializedName("RAW")
    val moneyData: MoneyData = MoneyData()
) {
    data class MoneyData(
        @SerializedName("USD")
        val coinValue: CryptoValue = CryptoValue()
    )
}
