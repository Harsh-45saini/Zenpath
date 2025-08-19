package com.example.zenpath.data.repository

import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.api.ApiInterface
import com.example.zenpath.data.model.DashboardResponse
import retrofit2.Call

class DashboardRepository(private val token: String) {
    private val apiService = ApiClient.getClient(token).create(ApiInterface::class.java)

    fun getDashboardData(): Call<DashboardResponse> {
        return apiService.getDashboardData("Bearer $token") // ✅ no need to pass token manually
    }
}