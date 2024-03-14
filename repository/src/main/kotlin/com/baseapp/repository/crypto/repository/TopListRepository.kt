package com.baseapp.repository.crypto.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.liveData
import com.baseapp.local.stockbit.dao.CryptoDao
import com.baseapp.local.stockbit.dao.RemoteKeysDao
import com.baseapp.repository.crypto.mediator.CryptoMediator
import com.model.crypto.CryptoBaseResponse
import com.model.crypto.crypto.ResponseListCryptoInfo
import com.network.crypto.crypto.datasource.TopListDataSource
import javax.inject.Inject

interface TopListRepository {
    suspend fun getListTopTier(): CryptoBaseResponse<String, List<ResponseListCryptoInfo>>

    suspend fun getListTopTierLocal(): List<ResponseListCryptoInfo>

    suspend fun checkDaoExist(): Boolean

    suspend fun clearAllData()

    fun getPagingTopTier(): LiveData<PagingData<ResponseListCryptoInfo>>

    fun getPagingTopTierLocal(): PagingSource<Int, ResponseListCryptoInfo>
}

class TopListRepositoryImpl @Inject constructor(
    private val datasource: TopListDataSource,
    private val dao: CryptoDao,
    private val remoteKeyDao: RemoteKeysDao
) : TopListRepository {

    override suspend fun getListTopTier(): CryptoBaseResponse<String, List<ResponseListCryptoInfo>> =
        datasource.fetchTopUsersAsync()

    override suspend fun getListTopTierLocal(): List<ResponseListCryptoInfo> =
        dao.getAllListCrypto()

    override suspend fun checkDaoExist() = dao.checkListExist() != null

    override suspend fun clearAllData() {
        dao.deleteAll()
        remoteKeyDao.clearRemoteKeys()
    }

    override fun getPagingTopTier(): LiveData<PagingData<ResponseListCryptoInfo>> =
        @OptIn(ExperimentalPagingApi::class)
        Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { getPagingTopTierLocal() },
            remoteMediator = CryptoMediator(
                service = datasource,
                dao = dao,
                remoteKeyDao = remoteKeyDao
            )
            // CryptoPagingSource(datasource)
        ).liveData

    override fun getPagingTopTierLocal(): PagingSource<Int, ResponseListCryptoInfo> =
        dao.getListCryptoPagination()
}