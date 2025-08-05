// DashboardRepository.kt
package com.example.zenpath.data.repository

import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.api.ApiInterface
import com.example.zenpath.data.model.DashboardResponse
import retrofit2.Call

class DashboardRepository {
    private val apiService = ApiClient.getClient().create(ApiInterface::class.java)

    fun getDashboardData(token: String): Call<DashboardResponse> {
        return apiService.getDashboardData("Bearer $token")
    }
}
