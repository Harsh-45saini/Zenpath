package com.example.zenpath.data.repository

import android.content.Context
import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.api.ApiInterface
import com.example.zenpath.data.local.PrefManager
import com.example.zenpath.data.model.DailyPracticeResponse
import retrofit2.Call

class MostPopularRepository(context: Context) {

    // Get PrefManager instance
    private val prefManager = PrefManager(context)

    // Pass token from PrefManager to ApiClient
    private val apiService: ApiInterface by lazy {
        val token = prefManager.getToken()
        ApiClient.getClient(token).create(ApiInterface::class.java)
    }

    fun getAllDailyPractices(): Call<DailyPracticeResponse> {
        return apiService.getAllDailyPractices()
    }

    fun getDailyPracticesByCategory(categoryId: Int): Call<DailyPracticeResponse> {
        return apiService.getDailyPractices(categoryId)
    }
}