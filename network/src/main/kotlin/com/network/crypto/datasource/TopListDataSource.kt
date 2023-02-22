package com.network.crypto.datasource

import com.network.crypto.service.TopListServices
import javax.inject.Inject

/**
 * Implementation of [TopListServices] interface
 */
class TopListDataSource @Inject constructor(private val topListServices: TopListServices) {
    suspend fun fetchTopUsersAsync(page: Int = 0) =
            topListServices.getTotalTopTierAsync(page = page)
}