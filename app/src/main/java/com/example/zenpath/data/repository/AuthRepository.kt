package com.example.zenpath.data.repository

import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.api.ApiInterface
import com.example.zenpath.data.model.LoginRequest
import com.example.zenpath.data.model.LoginResponse
import com.example.zenpath.data.model.RegisterRequest
import com.example.zenpath.data.model.RegisterResponse
import retrofit2.Call

class AuthRepository {
    private val apiService = ApiClient.getClient().create(ApiInterface::class.java)

    fun loginUser(request: LoginRequest): Call<LoginResponse> {
        return apiService.loginUser(request)
    }

    fun registerUser(request: RegisterRequest): Call<RegisterResponse> {
        return apiService.registerUser(request)
    }
}
