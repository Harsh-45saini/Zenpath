package com.example.zenpath.data.repository

import android.util.Log
import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.api.ApiInterface
import com.example.zenpath.data.model.CategoryResponse
import com.example.zenpath.data.model.DailyPracticeResponse
import retrofit2.Call

class DailyPracticeRepository(private val api: ApiInterface) {

    private val apiService = ApiClient.apiService

    fun getDailyPracticesByCategory(token: String, categoryId: Int): Call<DailyPracticeResponse> {
        return api.getDailyPracticesByCategory("Bearer $token", categoryId)
    }

    fun getCategoryById(token: String, categoryId:Int): Call<CategoryResponse> {
        Log.d("DEBUG_AUTH", "Using token: Bearer $token")
        return api.getCategoryById("Bearer $token", categoryId)
    }
}