package com.network.crypto.crypto.datasource

import com.network.crypto.crypto.service.TopListServices
import javax.inject.Inject

/**
 * Implementation of [TopListServices] interface
 */
class TopListDataSource @Inject constructor(private val topListServices: TopListServices) {
    suspend fun fetchTopUsersAsync(page: Int = 0) =
            topListServices.getTotalTopTierAsync(page = page)
}