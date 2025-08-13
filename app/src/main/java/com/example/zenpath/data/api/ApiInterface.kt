package com.example.zenpath.data.api

import com.example.zenpath.data.model.CategoriesResponse
import com.example.zenpath.data.model.DashboardResponse
import com.example.zenpath.data.model.LoginRequest
import com.example.zenpath.data.model.LoginResponse
import com.example.zenpath.data.model.PrivacyPolicyResponse
import com.example.zenpath.data.model.RegisterRequest
import com.example.zenpath.data.model.RegisterResponse
import com.example.zenpath.data.model.TermsAndConditionsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @POST("/api/users/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/api/users/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/api/dashboard")
    fun getDashboardData(@Header("Authorization") token: String): Call<DashboardResponse>

    // Public endpoint — no Authorization header needed

    @GET("/api/categories")
    fun getAllCategories(@Header("Authorization") token: String): Call<CategoriesResponse>

    @GET("/api/privacy-policy/latest")
    fun getPrivacyPolicy(): Call<PrivacyPolicyResponse>

    @GET("/api/terms-conditions/latest")
    fun getTermsOfService(): Call<TermsAndConditionsResponse>

}