package com.example.zenpath.data.repository

import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.api.ApiInterface
import com.example.zenpath.data.model.DailyPracticeResponse
import retrofit2.Call

class DailyPracticeRepository(private val api: ApiInterface) {

    private val apiService = ApiClient.apiService
    fun getDailyPracticesByCategory(categoryId: Int): Call<DailyPracticeResponse> {
        return api.getDailyPracticesByCategory(categoryId)
    }
}
