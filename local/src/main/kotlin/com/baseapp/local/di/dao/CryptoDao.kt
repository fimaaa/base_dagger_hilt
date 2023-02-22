package com.baseapp.local.di.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.baseapp.local.common.dao.BaseDao
import com.model.crypto.crypto.ResponseListCryptoInfo

@Dao
interface CryptoDao : BaseDao<ResponseListCryptoInfo> {

    @Query("SELECT * FROM ResponseListCryptoInfo ORDER BY page ASC")
    suspend fun getAllListCrypto(): List<ResponseListCryptoInfo>

    @Query("SELECT * FROM ResponseListCryptoInfo ORDER BY page ASC LIMIT 1")
    suspend fun checkListExist(): ResponseListCryptoInfo?

    @Query("SELECT * FROM ResponseListCryptoInfo ORDER BY page ASC")
    fun getListCryptoPagination(): PagingSource<Int, ResponseListCryptoInfo>

    @Query("DELETE FROM ResponseListCryptoInfo")
    suspend fun deleteAll()

    suspend fun save(data: ResponseListCryptoInfo) {
        insert(data)
    }

    suspend fun save(data: List<ResponseListCryptoInfo>) {
        insert(data)
    }
}