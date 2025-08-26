package com.example.zenpath.data.repository

import android.content.Context
import com.example.zenpath.data.model.CategoriesResponse
import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.api.ApiInterface
import com.example.zenpath.data.local.PrefManager
import retrofit2.Call

class CategoriesRepository (context: Context){
    private val prefManager = PrefManager(context)
    private val apiService: ApiInterface by lazy {
        val token = prefManager.getToken()
        ApiClient.getClient(token).create(ApiInterface::class.java)
    }

    fun getAllCategories(token: String): Call<CategoriesResponse> {
        return apiService.getAllCategories("Bearer ${prefManager.getToken()}")
    }
}
