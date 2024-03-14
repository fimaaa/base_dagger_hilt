package com.network.crypto.crypto.service

import com.model.crypto.CryptoBaseResponse
import com.model.crypto.crypto.ResponseListCryptoInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface TopListServices {

    @GET("top/totaltoptiervolfull")
    suspend fun getTotalTopTierAsync(
        @Query("tsym") tsym: String = "USD",
        @Query("limit") limit: Int = 50,
        @Query("page") page: Int = 0
    ): CryptoBaseResponse<String, List<ResponseListCryptoInfo>>
}