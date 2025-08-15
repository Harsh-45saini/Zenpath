package com.example.zenpath.data.repository

import com.example.zenpath.data.model.CategoriesResponse
import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.api.ApiInterface
import retrofit2.Call

class CategoriesRepository {
    private val apiService = ApiClient.getClient().create(ApiInterface::class.java)

    fun getAllCategories(token: String): Call<CategoriesResponse> {
        return apiService.getAllCategories("Bearer $token")
    }
}
