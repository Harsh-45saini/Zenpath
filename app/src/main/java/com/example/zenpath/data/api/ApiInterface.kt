package com.example.zenpath.data.api

import com.example.zenpath.data.model.DashboardResponse
import com.example.zenpath.data.model.LoginRequest
import com.example.zenpath.data.model.LoginResponse
import com.example.zenpath.data.model.RegisterRequest
import com.example.zenpath.data.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @POST("/users/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/users/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/api/dashboard")
    fun getDashboardData(@Header("Authorization") token: String): Call<DashboardResponse>
}